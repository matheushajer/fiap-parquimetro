package br.com.fiap.parquimetro.dto;

import br.com.fiap.parquimetro.entities.Estacionamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EstacionamentoDTO(
        LocalDateTime horaInicial,
        LocalDateTime horaFinal,
        BigDecimal valorTotal,
        BigDecimal valorHora,
        boolean isPeriodoEncerrado,
        int tempoPrevisto,
        Estacionamento.TipoDePermanencia tipoPermanencia,
        Long condutorId
) {
}
