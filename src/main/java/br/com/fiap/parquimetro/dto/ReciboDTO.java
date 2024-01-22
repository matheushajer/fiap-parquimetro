package br.com.fiap.parquimetro.dto;

import br.com.fiap.parquimetro.entities.Estacionamento;
import br.com.fiap.parquimetro.entities.MetodoDePagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReciboDTO(

        Long reciboId,
        String nomeCondutor,
        String cpfCondutor,
        String placa,
        String modelo,
        String cor,
        Estacionamento.TipoDePermanencia tipoPermanencia,
        BigDecimal valorHora,
        LocalDateTime horaInicial,
        LocalDateTime horaFinal,
        BigDecimal valorTotal,
        MetodoDePagamento.TipoDePagamento metodoDePagamento

) {

}
