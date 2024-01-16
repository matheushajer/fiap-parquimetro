package br.com.fiap.parquimetro.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_metodos_de_pagamento")
public class MetodoDePagamento {

    public enum TipoDePagamento {
        DEBITO,
        CREDITO,
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

    public MetodoDePagamento(TipoDePagamento metodoDePagamento) {
        this.metodoDePagamento = metodoDePagamento;
    }

    // ************************************************
    // Seleção Getter e Setter
    // ************************************************
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDePagamento getMetodoDePagamento() {
        return metodoDePagamento;
    }

    public void setMetodoDePagamento(TipoDePagamento metodoDePagamento) {
        this.metodoDePagamento = metodoDePagamento;
    }

    public Condutor getCondutor() {
        return condutor;
    }

    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }
}
