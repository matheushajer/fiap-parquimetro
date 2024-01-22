package br.com.fiap.parquimetro.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_recibo")
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCondutor;
    private String cpfCondutor;
    private String placa;
    private String modelo;
    private String cor;
    private Estacionamento.TipoDePermanencia tipoPermanencia;
    private BigDecimal valorHora;
    private LocalDateTime horaInicial;
    private LocalDateTime horaFinal;
    private BigDecimal valorTotal;
    private MetodoDePagamento.TipoDePagamento metodoDePagamento;

    @ManyToOne
    @JoinColumn(name = "condutor_id")
    private Condutor condutor;

    public Recibo(String nomeCondutor, String cpfCondutor, String placa, String modelo, String cor, Estacionamento.TipoDePermanencia tipoPermanencia, BigDecimal valorHora, LocalDateTime horaInicial, LocalDateTime horaFinal, BigDecimal valorTotal, MetodoDePagamento.TipoDePagamento metodoDePagamento) {
        this.nomeCondutor = nomeCondutor;
        this.cpfCondutor = cpfCondutor;
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.tipoPermanencia = tipoPermanencia;
        this.valorHora = valorHora;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.valorTotal = valorTotal;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Estacionamento.TipoDePermanencia getTipoPermanencia() {
        return tipoPermanencia;
    }

    public void setTipoPermanencia(Estacionamento.TipoDePermanencia tipoPermanencia) {
        this.tipoPermanencia = tipoPermanencia;
    }

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public LocalDateTime getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(LocalDateTime horaInicial) {
        this.horaInicial = horaInicial;
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

    public Condutor getCondutor() {
        return condutor;
    }

    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }
}
