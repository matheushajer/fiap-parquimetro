package br.com.fiap.parquimetro.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public record CondutorDTO(
        String nomeCondutor,
        String cpfCondutor,
        String emailCondutor,
        List<TelefoneDTO> telefonesCondutor,
        List<EnderecoDTO> enderecosCondutor,
        List<VeiculoDTO> veiculosCondutor,
        List<MetodoDePagamentoDTO> metodosCondutor,
        List<EstacionamentoDTO> estacionamentoCondutor,
        List<ReciboDTO> recibos
) {

}
