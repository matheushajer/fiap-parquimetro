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

    public MetodoDePagamento convertToEntity(MetodoDePagamentoDTO metodoDePagamentoDTO, Long conductorId) {
        MetodoDePagamento metodoDePagamento = new MetodoDePagamento(
                MetodoDePagamento.TipoDePagamento.valueOf(String.valueOf(metodoDePagamentoDTO.metodoDePgamento()))
        );

        // Estabelece a relação com o Condutor
        if (conductorId != null) {
            Condutor conductor = new Condutor();
            conductor.setId(conductorId);
            metodoDePagamento.setCondutor(conductor);
        }

        return metodoDePagamento;
    }

    public MetodoDePagamentoDTO createMetodoDePagamentoDTO(MetodoDePagamentoDTO metodoDePagamentoDTO, Long conductorId) {
        MetodoDePagamento metodoDePagamento = convertToEntity(metodoDePagamentoDTO, conductorId);
        MetodoDePagamento savedMetodoDePagamento = metodoDePagamentoRepository.save(metodoDePagamento);
        return convertToDTO(savedMetodoDePagamento);
    }

    public MetodoDePagamentoDTO updateMetodoDePagamentoDTO(Long id, MetodoDePagamentoDTO metodoDePagamentoDTO) {
        MetodoDePagamento existingMetodoDePagamento = getMetodoDePagamentoById(id);
        MetodoDePagamento updatedMetodoDePagamento = updateMetodoDePagamentoFromDTO(existingMetodoDePagamento, metodoDePagamentoDTO);
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
        if (metodoDePagamentoDTO.metodoDePgamento() != null) {
            metodoDePagamento.setMetodoDePagamento(MetodoDePagamento.TipoDePagamento.valueOf(String.valueOf(metodoDePagamentoDTO.metodoDePgamento())));
        }

        return metodoDePagamento;
    }
}
