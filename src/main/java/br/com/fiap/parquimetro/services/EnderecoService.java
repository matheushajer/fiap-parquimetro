package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.dto.EnderecoDTO;
import br.com.fiap.parquimetro.entities.Condutor;
import br.com.fiap.parquimetro.entities.Endereco;
import br.com.fiap.parquimetro.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public EnderecoDTO convertToDTO(Endereco endereco) {
        return new EnderecoDTO(
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf()
        );
    }

    public Endereco convertToEntity(EnderecoDTO enderecoDTO, Condutor condutor) {
        Endereco endereco = new Endereco(
                enderecoDTO.cep(),
                enderecoDTO.logradouro(),
                enderecoDTO.numero(),
                enderecoDTO.complemento(),
                enderecoDTO.bairro(),
                enderecoDTO.cidade(),
                enderecoDTO.uf()
        );

        // Estabelece a relação com o Condutor
        endereco.setCondutor(condutor);

        return endereco;
    }



    public EnderecoDTO createEnderecoDTO(EnderecoDTO enderecoDTO, Condutor condutor) {
        Endereco endereco = convertToEntity(enderecoDTO, condutor);
        Endereco savedEndereco = enderecoRepository.save(endereco);
        return convertToDTO(savedEndereco);
    }


    public EnderecoDTO updateEnderecoDTO(Long id, EnderecoDTO enderecoDTO, Condutor condutor) {
        Endereco existingEndereco = getEnderecoById(id);
        Endereco updatedEndereco = updateEnderecoFromDTO(existingEndereco, enderecoDTO);
        updatedEndereco.setCondutor(condutor); // Atualiza a referência ao Condutor
        Endereco savedEndereco = enderecoRepository.save(updatedEndereco);
        return convertToDTO(savedEndereco);
    }


    public void deleteEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }

    public Endereco getEnderecoById(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereco não encontrado pelo id: " + id));
    }

    private Endereco updateEnderecoFromDTO(Endereco endereco, EnderecoDTO enderecoDTO) {
        // Atualiza apenas os campos modificáveis
        endereco.setCep(enderecoDTO.cep());
        endereco.setLogradouro(enderecoDTO.logradouro());
        endereco.setNumero(enderecoDTO.numero());
        endereco.setComplemento(enderecoDTO.complemento());
        endereco.setBairro(enderecoDTO.bairro());
        endereco.setCidade(enderecoDTO.cidade());
        endereco.setUf(enderecoDTO.uf());

        return endereco;
    }
}
