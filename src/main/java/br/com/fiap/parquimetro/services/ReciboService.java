package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.dto.ReciboDTO;
import br.com.fiap.parquimetro.entities.Condutor;
import br.com.fiap.parquimetro.entities.Estacionamento;
import br.com.fiap.parquimetro.entities.Recibo;
import br.com.fiap.parquimetro.repositories.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReciboService {


    @Autowired
    private ReciboRepository reciboRepository;

    @Autowired
    public ReciboService(ReciboRepository reciboRepository) {
        this.reciboRepository = reciboRepository;
    }

    public ReciboDTO criarRecibo(Condutor condutor, Estacionamento estacionamento) {
        Recibo recibo = new Recibo();
        recibo.setCondutor(condutor);
        recibo.setNomeCondutor(condutor.getNome());
        recibo.setCpfCondutor(condutor.getCpf());
        recibo.setPlacaVeiculo(condutor.getVeiculos().get(0).getPlaca());
        recibo.setModeloVeiculo(condutor.getVeiculos().get(0).getModelo());
        recibo.setTipoPermanencia(estacionamento.getTipoPermanencia());
        recibo.setHoraInicio(estacionamento.getHoraInicial());
        recibo.setHoraFinal(estacionamento.getHoraFinal());
        recibo.setValorTotal(estacionamento.getValorTotal());
        recibo.setMetodoDePagamento(condutor.getFormaDePagamento().get(0).getMetodoDePagamento());

        Recibo savedRecibo = reciboRepository.save(recibo);
        return convertToDTO(savedRecibo);
    }

    public ReciboDTO convertToDTO(Recibo recibo) {
        return new ReciboDTO(
                recibo.getId(),
                recibo.getCondutor().getId(),
                recibo.getCondutor().getNome(),
                recibo.getCondutor().getCpf(),
                recibo.getPlacaVeiculo(),
                recibo.getModeloVeiculo(),
                recibo.getTipoPermanencia(),
                recibo.getHoraInicio(),
                recibo.getHoraFinal(),
                recibo.getValorTotal(),
                recibo.getMetodoDePagamento()
        );
    }
}



