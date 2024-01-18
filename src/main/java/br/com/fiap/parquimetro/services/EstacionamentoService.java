package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.dto.EstacionamentoDTO;
import br.com.fiap.parquimetro.entities.Estacionamento;
import br.com.fiap.parquimetro.repositories.CondutorRepository;
import br.com.fiap.parquimetro.repositories.EstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class EstacionamentoService {

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;
    @Autowired
    private CondutorRepository condutorRepository;

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

    public EstacionamentoDTO encerrarPeriodoDeEstacionamento(Long estacionamentoId) {
        Estacionamento estacionamento = estacionamentoRepository.findById(estacionamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Estacionamento não encontrado pelo ID: "
                        + estacionamentoId));
        EstacionamentoDTO estacionamentoDTO = convertToDTO(estacionamento);

        // Verificar se o período é FIXO ou VARIAVEL
        if (estacionamento.getTipoPermanencia() == Estacionamento.TipoDePermanencia.FIXO) {
            // Lógica para FIXO
            estacionamento.setPeriodoEncerrado(true);
        } else if (estacionamento.getTipoPermanencia() == Estacionamento.TipoDePermanencia.VARIAVEL) {
            // Lógica para VARIAVEL
            estacionamento.setPeriodoEncerrado(true);
            estacionamento.setHoraFinal(LocalDateTime.now());
            estacionamento.setValorTotal(calcularValorTotal(estacionamento));
        }

        // Salvar as alterações no repositório
        Estacionamento savedEstacionamento = estacionamentoRepository.save(estacionamento);

        return convertToDTO(savedEstacionamento);
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
            LocalDateTime horaFinal = LocalDateTime.now();

            // Arredonda para a próxima hora cheia
            if (horaFinal.getMinute() > 0 || horaFinal.getSecond() > 0 || horaFinal.getNano() > 0) {
                horaFinal = horaFinal.plusHours(1).withMinute(0).withSecond(0).withNano(0);
            }

            return horaFinal;
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

}
