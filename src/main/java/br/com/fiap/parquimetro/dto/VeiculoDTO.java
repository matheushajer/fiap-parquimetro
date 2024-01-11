package br.com.fiap.parquimetro.dto;

public record VeiculoDTO(String modelo,
                         String cor,
                         String placa,
                         Long condutorId) {
}