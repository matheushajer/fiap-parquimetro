package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.dto.VeiculoDTO;
import br.com.fiap.parquimetro.entities.Condutor;
import br.com.fiap.parquimetro.entities.Veiculo;
import br.com.fiap.parquimetro.repositories.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Veiculo save(Veiculo veiculo) {
        return this.veiculoRepository.save(veiculo);
    }

    public VeiculoDTO convertToDTO(Veiculo veiculo) {
        return new VeiculoDTO(
                veiculo.getModelo(),
                veiculo.getCor(),
                veiculo.getPlaca()
        );
    }

    public Veiculo convertToEntity(VeiculoDTO veiculoDTO, Long conductorId) {
        Veiculo veiculo = new Veiculo(
                veiculoDTO.modelo(),
                veiculoDTO.cor(),
                veiculoDTO.placa()
        );

        // Estabelece a relação com o Condutor
        if (conductorId != null) {
            Condutor conductor = new Condutor();
            conductor.setId(conductorId);
            veiculo.setCondutor(conductor);
        }

        return veiculo;
    }

    public VeiculoDTO createVeiculoDTO(VeiculoDTO veiculoDTO, Long conductorId) {
        Veiculo veiculo = convertToEntity(veiculoDTO, conductorId);
        Veiculo savedVeiculo = veiculoRepository.save(veiculo);
        return convertToDTO(savedVeiculo);
    }

    public VeiculoDTO updateVeiculoDTO(Long id, VeiculoDTO veiculoDTO) {
        Veiculo existingVeiculo = getVeiculoById(id);
        Veiculo updatedVeiculo = updateVeiculoFromDTO(existingVeiculo, veiculoDTO);
        Veiculo savedVeiculo = veiculoRepository.save(updatedVeiculo);
        return convertToDTO(savedVeiculo);
    }

    public void deleteVeiculo(Long id) {
        veiculoRepository.deleteById(id);
    }

    public Veiculo getVeiculoById(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veiculo não encontrado pelo id: " + id));
    }

    private Veiculo updateVeiculoFromDTO(Veiculo veiculo, VeiculoDTO veiculoDTO) {
        // Atualiza apenas os campos modificáveis
        if (veiculoDTO.modelo() != null) {
            veiculo.setModelo(veiculoDTO.modelo());
        }

        if (veiculoDTO.cor() != null) {
            veiculo.setCor(veiculoDTO.cor());
        }

        if (veiculoDTO.placa() != null) {
            veiculo.setPlaca(veiculoDTO.placa());
        }

        return veiculo;
    }
}
