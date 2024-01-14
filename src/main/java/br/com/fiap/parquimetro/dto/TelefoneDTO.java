package br.com.fiap.parquimetro.dto;

public record TelefoneDTO(
        int ddi,
        int ddd,
        int numeroTelefone,
        boolean isTelefonePrincipal
) {
}
