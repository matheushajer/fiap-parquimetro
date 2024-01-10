package br.com.fiap.parquimetro.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_metodos_de_pagamento")
public class MetodoDePagamento {

    public enum TipoDePagamento {
        CREDITO,
        DEBITO,
        PIX
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoDePagamento metodoDePagamento;

    @ManyToOne
    @JoinColumn(name = "condutor_id")
    private Condutor condutor;

}
