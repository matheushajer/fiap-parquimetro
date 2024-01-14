package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.dto.TelefoneDTO;
import br.com.fiap.parquimetro.entities.Condutor;
import br.com.fiap.parquimetro.entities.Telefone;
import br.com.fiap.parquimetro.repositories.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TelefoneService {

    @Autowired
    private TelefoneRepository telefoneRepository;

    public TelefoneDTO convertToDTO(Telefone telefone) {
        return new TelefoneDTO(
                telefone.getDdi(),
                telefone.getDdd(),
                telefone.getTelefone(),
                telefone.isTelefonePrincipal()
        );
    }

    public Telefone convertToEntity(TelefoneDTO telefoneDTO, Long conductorId) {
        Telefone telefone = new Telefone(
                telefoneDTO.ddi(),
                telefoneDTO.ddd(),
                telefoneDTO.numeroTelefone(),
                telefoneDTO.isTelefonePrincipal()
        );

        // Estabelece a relação com o Condutor
        if (conductorId != null) {
            Condutor conductor = new Condutor();
            conductor.setId(conductorId);
            telefone.setCondutor(conductor);
        }

        return telefone;
    }

    public TelefoneDTO createTelefoneDTO(TelefoneDTO telefoneDTO, Long conductorId) {
        Telefone telefone = convertToEntity(telefoneDTO, conductorId);
        Telefone savedTelefone = telefoneRepository.save(telefone);
        return convertToDTO(savedTelefone);
    }

    public TelefoneDTO updateTelefoneDTO(Long id, TelefoneDTO telefoneDTO) {
        Telefone existingTelefone = getTelefoneById(id);
        Telefone updatedTelefone = updateTelefoneFromDTO(existingTelefone, telefoneDTO);
        Telefone savedTelefone = telefoneRepository.save(updatedTelefone);
        return convertToDTO(savedTelefone);
    }

    public void deleteTelefone(Long id) {
        telefoneRepository.deleteById(id);
    }

    public Telefone getTelefoneById(Long id) {
        return telefoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Telefone não encontrado pelo id: " + id));
    }

    private Telefone updateTelefoneFromDTO(Telefone telefone, TelefoneDTO telefoneDTO) {
        // Atualiza apenas os campos modificáveis
        telefone.setDdi(telefoneDTO.ddi());
        telefone.setDdd(telefoneDTO.ddd());
        telefone.setTelefone(telefoneDTO.numeroTelefone());
        telefone.setTelefonePrincipal(telefoneDTO.isTelefonePrincipal());

        return telefone;
    }

    public Telefone updatePhoneFromDTO(Telefone telefone, TelefoneDTO telefoneDTO) {
        telefone.setDdi(telefoneDTO.ddi());
        telefone.setDdd(telefoneDTO.ddd());
        telefone.setTelefone(telefoneDTO.numeroTelefone());
        telefone.setTelefonePrincipal(telefoneDTO.isTelefonePrincipal());

        return telefone;
    }


}
