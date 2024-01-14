package br.com.fiap.parquimetro.dto;

import br.com.fiap.parquimetro.entities.MetodoDePagamento;

public record MetodoDePagamentoDTO(
        MetodoDePagamento.TipoDePagamento metodoDePgamento
) {
}
