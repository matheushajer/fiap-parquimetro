package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.dto.*;
import br.com.fiap.parquimetro.entities.Condutor;
import br.com.fiap.parquimetro.entities.Endereco;
import br.com.fiap.parquimetro.entities.Recibo;
import br.com.fiap.parquimetro.repositories.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReciboService {


        private ReciboRepository reciboRepository;


    @Autowired
    public ReciboService(ReciboRepository reciboRepository) {
        this.reciboRepository = reciboRepository;
    }

    @Transactional(readOnly = true)
    public List<ReciboDTO> getAllRecibos() {
        List<Recibo> recibo = reciboRepository.findAll();
        return recibo.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Recibo getReciboById(Long id) {
        return reciboRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recibo não encontrado pelo id: " + id));
    }

    @Transactional
    public ReciboDTO createRecibo(ReciboDTO reciboDTO, CondutorDTO condutorDTO, VeiculoDTO veiculoDTO, EstacionamentoDTO estacionamentoDTO, MetodoDePagamentoDTO metodoDePagamentoDTO, Condutor condutor) {
        Recibo recibo = convertToEntity(condutorDTO, veiculoDTO, estacionamentoDTO, metodoDePagamentoDTO, reciboDTO, condutor);
        // Salva o recibo no banco de dados
        Recibo savedRecibo = reciboRepository.save(recibo);
        return convertToDTO(savedRecibo);
    }

    public ReciboDTO convertToDTO(Recibo recibo) {
        return new ReciboDTO(
                recibo.getId(),
                recibo.getNomeCondutor(),
                recibo.getCpfCondutor(),
                recibo.getPlaca(),
                recibo.getModelo(),
                recibo.getCor(),
                recibo.getTipoPermanencia(),
                recibo.getValorHora(),
                recibo.getHoraInicial(),
                recibo.getHoraFinal(),
                recibo.getValorTotal(),
                recibo.getMetodoDePagamento()

                );
    }

    public Recibo convertToEntity(CondutorDTO condutorDTO, VeiculoDTO veiculoDTO, EstacionamentoDTO estacionamentoDTO, MetodoDePagamentoDTO metodoDePagamentoDTO, ReciboDTO reciboDTO, Condutor condutor) {
        Recibo recibo = new Recibo(
        condutorDTO.nomeCondutor(),
        condutorDTO.cpfCondutor(),
        veiculoDTO.placa(),
        veiculoDTO.modelo(),
        veiculoDTO.cor(),
        estacionamentoDTO.tipoPermanencia(),
        estacionamentoDTO.valorHora(),
        estacionamentoDTO.horaInicial(),
        estacionamentoDTO.horaFinal(),
        estacionamentoDTO.valorTotal(),
        metodoDePagamentoDTO.metodoDePagamento()
        );

        // Estabelece a relação com o Condutor
        recibo.setCondutor(condutor);

        return recibo;
    }

    @Transactional(readOnly = true)
    public String gerarNotaFiscal(Long reciboId) {
        Recibo recibo = reciboRepository.findById(reciboId)
                .orElseThrow(() -> new ResourceNotFoundException("Recibo não encontrado pelo ID: " + reciboId));

        String notaFiscal = "Nota Fiscal:\n";
        notaFiscal += "Nome do Condutor: " + recibo.getNomeCondutor() + "\n";
        notaFiscal += "CPF do Condutor: " + recibo.getCpfCondutor() + "\n";
        notaFiscal += "Placa do Veículo: " + recibo.getPlaca() + "\n";
        notaFiscal += "Modelo do Veículo: " + recibo.getModelo() + "\n";
        notaFiscal += "Cor do Veículo: " + recibo.getCor() + "\n";
        notaFiscal += "Tipo de Permanência: " + recibo.getTipoPermanencia() + "\n";
        notaFiscal += "Valor da Hora: " + recibo.getValorHora() + "\n";
        notaFiscal += "Início do Período de Estacionamento: " + recibo.getHoraFinal() + "\n";
        notaFiscal += "Fim do Período de Estacionamento: " + recibo.getHoraFinal() + "\n";
        notaFiscal += "Método de Pagamento " + recibo.getMetodoDePagamento() + "\n";
        notaFiscal += "Tempo Total do Estacionamento: " + recibo.getValorTotal() + "\n";

        // Exemplo: Imprimir a nota fiscal no console
        System.out.println(notaFiscal);

        return notaFiscal;
    }
}



