package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.dto.*;
import br.com.fiap.parquimetro.entities.*;
import br.com.fiap.parquimetro.repositories.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private TelefoneService telefoneService;
    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private VeiculoService veiculoService;
    @Autowired
    private MetodoDePagamentoService metodoDePagamentoService;

    public CondutorDTO convertToDTO(Condutor condutor) {
        List<TelefoneDTO> telefonesDTO = condutor.getTelefones().stream()
                .map(telefoneService::convertToDTO)
                .collect(Collectors.toList());

        List<EnderecoDTO> enderecosDTO = condutor.getEnderecos().stream()
                .map(enderecoService::convertToDTO)
                .collect(Collectors.toList());

        List<VeiculoDTO> veiculosDTO = condutor.getVeiculos().stream()
                .map(veiculoService::convertToDTO)
                .collect(Collectors.toList());

        List<MetodoDePagamentoDTO> metodosDTO = condutor.getFormaDePagamento().stream()
                .map(metodoDePagamentoService::convertToDTO)
                .collect(Collectors.toList());

        return new CondutorDTO(
                condutor.getNome(),
                condutor.getCpf(),
                condutor.getEmail(),
                telefonesDTO,
                enderecosDTO,
                veiculosDTO,
                metodosDTO
        );
    }

    public Condutor convertToEntity(CondutorDTO condutorDTO) {
        Condutor condutor = new Condutor(
                condutorDTO.nomeCondutor(),
                condutorDTO.cpfCondutor(),
                condutorDTO.emailCondutor()
        );

        // Mapeia os telefones
        if (condutorDTO.telefonesCondutor() != null) {
            List<Telefone> telefones = condutorDTO.telefonesCondutor().stream()
                    .map(dto -> telefoneService.convertToEntity(dto, null))
                    .collect(Collectors.toList());
            condutor.setTelefones(telefones);
        }

        // Mapeia os endereços
        if (condutorDTO.enderecosCondutor() != null) {
            List<Endereco> enderecos = condutorDTO.enderecosCondutor().stream()
                    .map(dto -> enderecoService.convertToEntity(dto, null))
                    .collect(Collectors.toList());
            condutor.setEnderecos(enderecos);
        }

        // Mapeia os veículos
        if (condutorDTO.veiculosCondutor() != null) {
            List<Veiculo> veiculos = condutorDTO.veiculosCondutor().stream()
                    .map(dto -> veiculoService.convertToEntity(dto, null))
                    .collect(Collectors.toList());
            condutor.setVeiculos(veiculos);
        }

        // Mapeia os métodos de pagamento
        if (condutorDTO.metodosCondutor() != null) {
            List<MetodoDePagamento> metodos = condutorDTO.metodosCondutor().stream()
                    .map(dto -> metodoDePagamentoService.convertToEntity(dto, null))
                    .collect(Collectors.toList());
            condutor.setFormaDePagamento(metodos);
        }

        return condutor;
    }

    public CondutorDTO createCondutorDTO(CondutorDTO condutorDTO) {
        Condutor condutor = convertToEntity(condutorDTO);
        Condutor savedCondutor = condutorRepository.save(condutor);
        return convertToDTO(savedCondutor);
    }

    public CondutorDTO updateCondutorDTO(Long id, CondutorDTO condutorDTO) {
        CondutorDTO existingCondutor = getCondutorById(id);

        // Cria um novo objeto Condutor
        Condutor updatedCondutor = new Condutor();
        updatedCondutor.setNome(existingCondutor.nomeCondutor());
        updatedCondutor.setCpf(existingCondutor.cpfCondutor());
        updatedCondutor.setEmail(existingCondutor.emailCondutor());

        // Atualiza os campos modificáveis
        if (condutorDTO.nomeCondutor() != null) {
            updatedCondutor.setNome(condutorDTO.nomeCondutor());
        }

        if (condutorDTO.cpfCondutor() != null) {
            updatedCondutor.setCpf(condutorDTO.cpfCondutor());
        }

        if (condutorDTO.emailCondutor() != null) {
            updatedCondutor.setEmail(condutorDTO.emailCondutor());
        }

        // Atualiza os telefones, endereços, veículos e métodos de pagamento
        updateCollectionsFromDTO(updatedCondutor, condutorDTO);

        // Salva o novo objeto Condutor no banco de dados
        Condutor savedCondutor = condutorRepository.save(updatedCondutor);

        return convertToDTO(savedCondutor);
    }

    public void deleteCondutor(Long id) {
        condutorRepository.deleteById(id);
    }

    public List<CondutorDTO> getAllCondutores() {
        List<Condutor> condutores = condutorRepository.findAll();
        return condutores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CondutorDTO getCondutorById(Long id) {
        Condutor condutor = condutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Condutor not found with id: " + id));
        return convertToDTO(condutor);
    }

    private Condutor updateCondutorFromDTO(Condutor condutor, CondutorDTO condutorDTO) {
        // Atualiza apenas os campos modificáveis
        if (condutorDTO.nomeCondutor() != null) {
            condutor.setNome(condutorDTO.nomeCondutor());
        }

        if (condutorDTO.cpfCondutor() != null) {
            condutor.setCpf(condutorDTO.cpfCondutor());
        }

        if (condutorDTO.emailCondutor() != null) {
            condutor.setEmail(condutorDTO.emailCondutor());
        }

        // Atualiza os telefones, endereços, veículos e métodos de pagamento
        updateCollectionsFromDTO(condutor, condutorDTO);

        return condutor;
    }

    private void updateCollectionsFromDTO(Condutor condutor, CondutorDTO condutorDTO) {
        // Atualiza telefones
        if (condutorDTO.telefonesCondutor() != null) {
            List<Telefone> telefones = condutorDTO.telefonesCondutor().stream()
                    .map(dto -> telefoneService.convertToEntity(dto, condutor.getId()))
                    .collect(Collectors.toList());
            condutor.setTelefones(telefones);
        }

        // Atualiza endereços
        if (condutorDTO.enderecosCondutor() != null) {
            List<Endereco> enderecos = condutorDTO.enderecosCondutor().stream()
                    .map(dto -> enderecoService.convertToEntity(dto, condutor.getId()))
                    .collect(Collectors.toList());
            condutor.setEnderecos(enderecos);
        }

        // Atualiza veículos
        if (condutorDTO.veiculosCondutor() != null) {
            List<Veiculo> veiculos = condutorDTO.veiculosCondutor().stream()
                    .map(dto -> veiculoService.convertToEntity(dto, condutor.getId()))
                    .collect(Collectors.toList());
            condutor.setVeiculos(veiculos);
        }

        // Atualiza métodos de pagamento
        if (condutorDTO.metodosCondutor() != null) {
            List<MetodoDePagamento> metodos = condutorDTO.metodosCondutor().stream()
                    .map(dto -> metodoDePagamentoService.convertToEntity(dto, condutor.getId()))
                    .collect(Collectors.toList());
            condutor.setFormaDePagamento(metodos);
        }
    }

}
