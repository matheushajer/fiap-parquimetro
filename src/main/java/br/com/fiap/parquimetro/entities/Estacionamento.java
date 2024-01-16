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
}
