package br.com.fiap.parquimetro.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Estacionamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime horaInicial;
    private LocalDateTime  horaFinal;
    private BigDecimal valorTotal;
    private boolean isPeriodoEncerrado;

    // ************************************************
    // Seleção Getter e Setter
    // ************************************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isPeriodoEncerrado() {
        return isPeriodoEncerrado;
    }

    public void setPeriodoEncerrado(boolean periodoEncerrado) {
        isPeriodoEncerrado = periodoEncerrado;
    }
}
