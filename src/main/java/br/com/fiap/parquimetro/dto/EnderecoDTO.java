package br.com.fiap.parquimetro.dto;

public record EnderecoDTO(
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf
        ) {
}
