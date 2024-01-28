package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.dto.EstacionamentoDTO;
import br.com.fiap.parquimetro.dto.ReciboDTO;
import br.com.fiap.parquimetro.entities.Condutor;
import br.com.fiap.parquimetro.entities.Estacionamento;
import br.com.fiap.parquimetro.repositories.CondutorRepository;
import br.com.fiap.parquimetro.repositories.EstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Component @EnableScheduling
public class EstacionamentoService {

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;
    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private ReciboService reciboService;

    @Autowired
    private NotificationService notificationService;

    public EstacionamentoDTO iniciarPeriodoDeEstacionamento(EstacionamentoDTO estacionamentoDTO) {
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setCondutor(condutorRepository.findById(estacionamentoDTO.condutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado pelo ID: "
                        + estacionamentoDTO.condutorId())));
        estacionamento.setTipoPermanencia(estacionamentoDTO.tipoPermanencia());
        estacionamento.setHoraInicial(LocalDateTime.now());
        estacionamento.setTempoPrevisto(estacionamentoDTO.tempoPrevisto());

        if (estacionamentoDTO.tipoPermanencia() == Estacionamento.TipoDePermanencia.FIXO) {
            estacionamento.setHoraFinal(calcularHoraFinal(estacionamentoDTO));
            estacionamento.setValorTotal(calcularValorTotal(estacionamento));
        }

        Estacionamento savedEstacionamento = estacionamentoRepository.save(estacionamento);
        return convertToDTO(savedEstacionamento);
    }

    public ReciboDTO encerrarPeriodoDeEstacionamento(Long estacionamentoId) {
        Estacionamento estacionamento = estacionamentoRepository.findById(estacionamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Estacionamento não encontrado pelo ID: "
                        + estacionamentoId));
        EstacionamentoDTO estacionamentoDTO = convertToDTO(estacionamento);

        // Verificar se o período é FIXO ou VARIAVEL
        if (estacionamento.getTipoPermanencia() == Estacionamento.TipoDePermanencia.FIXO) {
            // Lógica para FIXO
            estacionamento.setPeriodoEncerrado(true);
            var condutor = condutorRepository.findById(estacionamentoDTO.condutorId());
            notificationService.validateCondutorSMS(condutor.get().getCpf(), "Periodo de estacionamento fixo encerrado.");
            notificationService.validateCondutorEMAIL(condutor.get().getCpf(), "AVISO - Estacionamento", "Periodo de estacionamento fixo encerrado.");
        } else if (estacionamento.getTipoPermanencia() == Estacionamento.TipoDePermanencia.VARIAVEL) {
            // Lógica para VARIAVEL
            estacionamento.setPeriodoEncerrado(true);
            estacionamento.setHoraFinal(calcularHoraFinal(estacionamentoDTO));
            estacionamento.setValorTotal(calcularValorTotal(estacionamento));
        }

        // Salvar as alterações no repositório
        Estacionamento savedEstacionamento = estacionamentoRepository.save(estacionamento);

        // Criando recibo
        ReciboDTO reciboDTO = reciboService.criarRecibo(estacionamento.getCondutor(), savedEstacionamento);


        return reciboDTO;
    }

    public EstacionamentoDTO convertToDTO(Estacionamento estacionamento) {
        return new EstacionamentoDTO(
                estacionamento.getHoraInicial(),
                estacionamento.getHoraFinal(),
                estacionamento.getValorTotal(),
                estacionamento.getValorHora(),
                estacionamento.isPeriodoEncerrado(),
                estacionamento.getTempoPrevisto(),
                estacionamento.getTipoPermanencia(),
                estacionamento.getCondutor().getId()
        );
    }

    private LocalDateTime calcularHoraFinal(EstacionamentoDTO estacionamentoDTO) {
        if (estacionamentoDTO.tipoPermanencia() == Estacionamento.TipoDePermanencia.FIXO) {
            return LocalDateTime.now().plusHours(estacionamentoDTO.tempoPrevisto());
        } else {
            LocalDateTime horaFinalInicial = LocalDateTime.now();

            // Arredonda para cima para a próxima hora cheia
            if (horaFinalInicial.getMinute() > 0 || horaFinalInicial.getSecond() > 0 || horaFinalInicial.getNano() > 0) {
                horaFinalInicial = horaFinalInicial.plusHours(1);
            }

            // Garante que a hora final seja uma diferença exata de horas da hora inicial
            long horasDeDiferenca = ChronoUnit.HOURS.between(estacionamentoDTO.horaInicial(), horaFinalInicial);

            // Se a diferença for menor que uma hora, ajusta para a próxima hora cheia
            if (horasDeDiferenca < 1) {
                horaFinalInicial = horaFinalInicial.plusHours(1);
            }

            return horaFinalInicial;
        }
    }

    private BigDecimal calcularValorTotal(Estacionamento estacionamento) {
        if (estacionamento.getHoraFinal() == null) {
            return BigDecimal.ZERO;
        }

        Duration duracao = Duration.between(estacionamento.getHoraInicial(), estacionamento.getHoraFinal());
        long horas = duracao.toHours();
        return estacionamento.getValorHora().multiply(BigDecimal.valueOf(horas));
    }

    // Método agendado para executar a cada hora
    //@Scheduled(cron = "0 0 * * * *")
    @Scheduled(fixedRate = 60000)
    public String sendHourlyNotification() {
        List<Long> estacionamentoIds = estacionamentoRepository.findAllId();

        for (Long id : estacionamentoIds) {
            Optional<Estacionamento> estacionamentoOpt = estacionamentoRepository.findById(id);
            if (estacionamentoOpt.isPresent()) {
                Estacionamento estacionamento = estacionamentoOpt.get();
                if (estacionamento.getHoraFinal() == null && estacionamento.getHoraInicial() != null) {
                    LocalDateTime now = LocalDateTime.now();
                    long diffInMinutes = ChronoUnit.MINUTES.between(estacionamento.getHoraInicial(), now);

                    // Se passou pelo menos 60 minutos desde a hora inicial
                    if (diffInMinutes >= 60 && diffInMinutes % 60 == 0)  {
                    var condutorOpt = condutorRepository.findById(id);
                    if (condutorOpt.isPresent()) {
                        Condutor condutor = condutorOpt.get();
                        notificationService.validateCondutorEMAIL(condutor.getCpf(), "AVISO - Estacionamento", "Acrescimo de 1 hora no seu tempo de estacionsmento variavel.");
                        notificationService.validateCondutorSMS(condutor.getCpf(), "Acrescimo de 1 hora no seu tempo de estacionsmento variavel.");
                    }
                    }
                }
            }
            return "Notificação de hora enviada com sucesso.";
        }
        return "Nao possui registros para envio.";
    }
}
