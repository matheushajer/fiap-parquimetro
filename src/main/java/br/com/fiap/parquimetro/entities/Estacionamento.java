package br.com.fiap.parquimetro.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_estacionamento")
public class Estacionamento {

    public enum TipoDePermanencia {
        FIXO,
        VARIAVEL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "condutor_id")
    private Condutor condutor;

    private LocalDateTime horaInicial = LocalDateTime.now();
    private LocalDateTime horaFinal;
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private BigDecimal valorHora = BigDecimal.valueOf(20.00);
    private boolean isPeriodoEncerrado = false;
    private int tempoPrevisto;

    @Enumerated(EnumType.STRING)
    private TipoDePermanencia tipoPermanencia;

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

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public boolean isPeriodoEncerrado() {
        return isPeriodoEncerrado;
    }

    public void setPeriodoEncerrado(boolean periodoEncerrado) {
        isPeriodoEncerrado = periodoEncerrado;
    }

    public int getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(int tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }

    public TipoDePermanencia getTipoPermanencia() {
        return tipoPermanencia;
    }

    public void setTipoPermanencia(TipoDePermanencia tipoPermanencia) {
        this.tipoPermanencia = tipoPermanencia;
    }
}
