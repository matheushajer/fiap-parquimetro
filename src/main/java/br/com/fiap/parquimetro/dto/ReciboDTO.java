package br.com.fiap.parquimetro.dto;

import br.com.fiap.parquimetro.entities.Estacionamento;
import br.com.fiap.parquimetro.entities.MetodoDePagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReciboDTO(
        Long id,
        Long condutorId,
        String nomeCondutor,
        String cpfCondutor,
        String placaVeiculo,
        String modeloVeiculo,
        Estacionamento.TipoDePermanencia tipoPermanencia,
        LocalDateTime horaInicio,
        LocalDateTime horaFinal,
        BigDecimal valorTotal,
        MetodoDePagamento.TipoDePagamento tipoDePagamento
) {
}
