package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.dto.MetodoDePagamentoDTO;
import br.com.fiap.parquimetro.entities.Condutor;
import br.com.fiap.parquimetro.entities.MetodoDePagamento;
import br.com.fiap.parquimetro.repositories.MetodoDePagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MetodoDePagamentoService {

    @Autowired
    private MetodoDePagamentoRepository metodoDePagamentoRepository;

    public MetodoDePagamentoDTO convertToDTO(MetodoDePagamento metodoDePagamento) {
        return new MetodoDePagamentoDTO(
                metodoDePagamento.getMetodoDePagamento()
        );
    }

    public MetodoDePagamento convertToEntity(MetodoDePagamentoDTO metodoDePagamentoDTO, Condutor condutor) {
        MetodoDePagamento metodoDePagamento = new MetodoDePagamento(
                metodoDePagamentoDTO.metodoDePagamento()
        );

        // Estabelece a relação com o Condutor
        metodoDePagamento.setCondutor(condutor);

        return metodoDePagamento;
    }


    public MetodoDePagamentoDTO createMetodoDePagamentoDTO(MetodoDePagamentoDTO metodoDePagamentoDTO, Condutor condutor) {
        MetodoDePagamento metodoDePagamento = convertToEntity(metodoDePagamentoDTO, condutor);
        MetodoDePagamento savedMetodoDePagamento = metodoDePagamentoRepository.save(metodoDePagamento);
        return convertToDTO(savedMetodoDePagamento);
    }


    public MetodoDePagamentoDTO updateMetodoDePagamentoDTO(Long id, MetodoDePagamentoDTO metodoDePagamentoDTO, Condutor condutor) {
        MetodoDePagamento existingMetodoDePagamento = getMetodoDePagamentoById(id);
        MetodoDePagamento updatedMetodoDePagamento = updateMetodoDePagamentoFromDTO(existingMetodoDePagamento, metodoDePagamentoDTO);
        updatedMetodoDePagamento.setCondutor(condutor); // Atualiza a referência ao Condutor
        MetodoDePagamento savedMetodoDePagamento = metodoDePagamentoRepository.save(updatedMetodoDePagamento);
        return convertToDTO(savedMetodoDePagamento);
    }


    public void deleteMetodoDePagamento(Long id) {
        metodoDePagamentoRepository.deleteById(id);
    }

    public MetodoDePagamento getMetodoDePagamentoById(Long id) {
        return metodoDePagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MetodoDePagamento não encontrado pelo id: " + id));
    }

    private MetodoDePagamento updateMetodoDePagamentoFromDTO(MetodoDePagamento metodoDePagamento, MetodoDePagamentoDTO metodoDePagamentoDTO) {
        // Atualiza apenas os campos modificáveis
        if (metodoDePagamentoDTO.metodoDePagamento() != null) {
            metodoDePagamento.setMetodoDePagamento(MetodoDePagamento.TipoDePagamento.valueOf(String.valueOf(metodoDePagamentoDTO.metodoDePagamento())));
        }

        return metodoDePagamento;
    }
}
