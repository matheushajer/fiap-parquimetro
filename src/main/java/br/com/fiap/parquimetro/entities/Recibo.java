package br.com.fiap.parquimetro.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Table(name = "tb_recibo")
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "condutor_id", nullable = false)
    private Condutor condutor;

    @Column(nullable = false)
    private String nomeCondutor;

    @Column(nullable = false)
    private String cpfCondutor;

    @Column(nullable = false)
    private String placaVeiculo;

    @Column(nullable = false)
    private String modeloVeiculo;

    @Enumerated(EnumType.STRING)
    private Estacionamento.TipoDePermanencia tipoPermanencia;

    private LocalDateTime horaInicio;

    private LocalDateTime horaFinal;

    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private MetodoDePagamento.TipoDePagamento metodoDePagamento;

    public Recibo() {
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

    public Condutor getCondutor() {
        return condutor;
    }

    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }

    public String getNomeCondutor() {
        return nomeCondutor;
    }

    public void setNomeCondutor(String nomeCondutor) {
        this.nomeCondutor = nomeCondutor;
    }

    public String getCpfCondutor() {
        return cpfCondutor;
    }

    public void setCpfCondutor(String cpfCondutor) {
        this.cpfCondutor = cpfCondutor;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public Estacionamento.TipoDePermanencia getTipoPermanencia() {
        return tipoPermanencia;
    }

    public void setTipoPermanencia(Estacionamento.TipoDePermanencia tipoPermanencia) {
        this.tipoPermanencia = tipoPermanencia;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(LocalDateTime horaFinal) {
        this.horaFinal = horaFinal;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public MetodoDePagamento.TipoDePagamento getMetodoDePagamento() {
        return metodoDePagamento;
    }

    public void setMetodoDePagamento(MetodoDePagamento.TipoDePagamento metodoDePagamento) {
        this.metodoDePagamento = metodoDePagamento;
    }
}
