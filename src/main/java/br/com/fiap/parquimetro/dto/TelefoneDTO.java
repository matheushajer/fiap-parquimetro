package br.com.fiap.parquimetro.dto;

import lombok.Getter;
import lombok.Setter;


public record TelefoneDTO(
        int ddi,
        int ddd,
        int numeroTelefone,
        boolean isTelefonePrincipal
) {
}
