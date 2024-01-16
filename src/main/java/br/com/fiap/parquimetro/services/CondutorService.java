package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.dto.*;
import br.com.fiap.parquimetro.entities.*;
import br.com.fiap.parquimetro.repositories.CondutorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public CondutorDTO convertToDTO(Condutor condutor) {
        List<TelefoneDTO> telefonesDTO = (condutor.getTelefones() != null)
                ? condutor.getTelefones().stream().map(telefoneService::convertToDTO).collect(Collectors.toList())
                : new ArrayList<>();

        List<EnderecoDTO> enderecosDTO = (condutor.getEnderecos() != null)
                ? condutor.getEnderecos().stream().map(enderecoService::convertToDTO).collect(Collectors.toList())
                : new ArrayList<>();

        List<VeiculoDTO> veiculosDTO = (condutor.getVeiculos() != null)
                ? condutor.getVeiculos().stream().map(veiculoService::convertToDTO).collect(Collectors.toList())
                : new ArrayList<>();

        List<MetodoDePagamentoDTO> metodosDTO = (condutor.getFormaDePagamento() != null)
                ? condutor.getFormaDePagamento().stream().map(metodoDePagamentoService::convertToDTO).collect(Collectors.toList())
                : new ArrayList<>();

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

    @Transactional
    public CondutorDTO createCondutorDTO(CondutorDTO condutorDTO) {
        // Converter DTO para a entidade Condutor
        Condutor condutor = convertToEntity(condutorDTO);

        // Adicionar associações ao Condutor
        if (condutorDTO.telefonesCondutor() != null) {
            // Verifica se há mais de um telefone principal
            long telefonesPrincipais = condutorDTO.telefonesCondutor().stream()
                    .filter(TelefoneDTO::isTelefonePrincipal)
                    .count();

            if (telefonesPrincipais != 1) {
                throw new IllegalArgumentException("Deve haver apenas um telefone marcado como principal.");
            }

            List<Telefone> telefones = condutorDTO.telefonesCondutor().stream()
                    .map(dto -> telefoneService.convertToEntity(dto, condutor))
                    .collect(Collectors.toList());
            condutor.setTelefones(telefones);
        }

        if (condutorDTO.enderecosCondutor() != null){
            List<Endereco> enderecos = condutorDTO.enderecosCondutor().stream()
                    .map(dto -> enderecoService.convertToEntity(dto, condutor))
                    .collect(Collectors.toList());
            condutor.setEnderecos(enderecos);
        }

        if (condutorDTO.veiculosCondutor() != null) {
            List<Veiculo> veiculos = condutorDTO.veiculosCondutor().stream()
                    .map(dto -> veiculoService.convertToEntity(dto, condutor))
                    .collect(Collectors.toList());
            condutor.setVeiculos(veiculos);
        }

        if (condutorDTO.metodosCondutor() != null) {
            List<MetodoDePagamento> metodos = condutorDTO.metodosCondutor().stream()
                    .map(dto -> metodoDePagamentoService.convertToEntity(dto, condutor))
                    .collect(Collectors.toList());
            condutor.setFormaDePagamento(metodos);
        }

        // Salvar o Condutor no banco de dados
        Condutor savedCondutor = condutorRepository.save(condutor);

        // Retornar o DTO após a operação de salvamento
        return convertToDTO(savedCondutor);
    }


    public CondutorDTO updateCondutorDTO(Long id, CondutorDTO condutorDTO) {
        Condutor existingCondutor = condutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado pelo id: " + id));

        // Atualiza os campos modificáveis
        if (condutorDTO.nomeCondutor() != null) {
            existingCondutor.setNome(condutorDTO.nomeCondutor());
        }

        if (condutorDTO.cpfCondutor() != null) {
            existingCondutor.setCpf(condutorDTO.cpfCondutor());
        }

        if (condutorDTO.emailCondutor() != null) {
            existingCondutor.setEmail(condutorDTO.emailCondutor());
        }

        // Atualiza os telefones, endereços, veículos e métodos de pagamento
        updateCollectionsFromDTO(existingCondutor, condutorDTO);

        // Salva a entidade existente no banco de dados
        Condutor savedCondutor = condutorRepository.save(existingCondutor);

        return convertToDTO(savedCondutor);
    }

    public void deleteCondutor(Long id) {
        condutorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CondutorDTO> getAllCondutores() {
        List<Condutor> condutores = condutorRepository.findAll();

        for (Condutor condutor : condutores) {
            Hibernate.initialize(condutor.getTelefones());
        }

        return condutores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CondutorDTO getCondutorById(Long id) {
        Condutor condutor = condutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado pelo id: " + id));

        // Inicialize as coleções antes de retornar
        Hibernate.initialize(condutor.getTelefones());
        Hibernate.initialize(condutor.getEnderecos());
        Hibernate.initialize(condutor.getVeiculos());
        Hibernate.initialize(condutor.getFormaDePagamento());

        return convertToDTO(condutor);
    }

    private void updateCollectionsFromDTO(Condutor condutor, CondutorDTO condutorDTO) {
        // Atualiza telefones
        if (condutorDTO.telefonesCondutor() != null) {
            List<Telefone> telefones = condutorDTO.telefonesCondutor().stream()
                    .map(dto -> telefoneService.convertToEntity(dto, condutor))
                    .collect(Collectors.toList());

            // Verifica se mais de um telefone está marcado como principal
            long countPrincipal = telefones.stream().filter(Telefone::isTelefonePrincipal).count();
            if (countPrincipal > 1) {
                throw new IllegalArgumentException("Deve haver apenas um telefone marcado como principal.");
            }

            condutor.setTelefones(telefones);
        }

        // Atualiza endereços
        if (condutorDTO.enderecosCondutor() != null) {
            List<Endereco> enderecos = condutorDTO.enderecosCondutor().stream()
                    .map(dto -> enderecoService.convertToEntity(dto, condutor))
                    .collect(Collectors.toList());
            condutor.setEnderecos(enderecos);
        }

        // Atualiza veículos
        if (condutorDTO.veiculosCondutor() != null) {
            List<Veiculo> veiculos = condutorDTO.veiculosCondutor().stream()
                    .map(dto -> veiculoService.convertToEntity(dto, condutor))
                    .collect(Collectors.toList());
            condutor.setVeiculos(veiculos);
        }

        // Atualiza métodos de pagamento
        if (condutorDTO.metodosCondutor() != null) {
            List<MetodoDePagamento> metodos = condutorDTO.metodosCondutor().stream()
                    .map(dto -> metodoDePagamentoService.convertToEntity(dto, condutor))
                    .collect(Collectors.toList());
            condutor.setFormaDePagamento(metodos);
        }
    }

    @Transactional(readOnly = true)
    public CondutorDTO adicionarNovosTelefonesAoCondutor(Long condutorId, List<TelefoneDTO> novosTelefonesDTO) {
        Condutor condutor = condutorRepository.findById(condutorId)
                .orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado pelo ID: " + condutorId));

        // Convertendo DTOs para entidades e estabelecendo a relação com o condutor
        List<Telefone> novosTelefones = novosTelefonesDTO.stream()
                .map(dto -> telefoneService.convertToEntity(dto, condutor))
                .collect(Collectors.toList());

        // Adicionando os novos telefones ao condutor existente
        condutor.getTelefones().addAll(novosTelefones);

        // Validando se há no máximo um telefone principal
        long telefonesPrincipais = condutor.getTelefones().stream().filter(Telefone::isTelefonePrincipal).count()
                + novosTelefones.stream().filter(Telefone::isTelefonePrincipal).count();

        if (telefonesPrincipais != 1) {
            throw new IllegalArgumentException("Deve haver apenas um telefone marcado como principal.");
        }

        // Salvando o condutor atualizado
        Condutor savedCondutor = condutorRepository.save(condutor);

        // Convertendo e retornando o DTO atualizado
        return convertToDTO(savedCondutor);
    }

    public CondutorDTO removerTelefonesDoCondutor(Long condutorId, List<Integer> ordens) {
        Condutor condutor = condutorRepository.findById(condutorId)
                .orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado pelo ID: " + condutorId));

        List<Telefone> telefones = condutor.getTelefones();

        // Filtra os telefones que não devem ser removidos
        List<Telefone> telefonesAtualizados = telefones.stream()
                .filter(telefone -> !ordens.contains(telefones.indexOf(telefone) + 1))
                .collect(Collectors.toList());

        // Atualiza a lista de telefones no condutor
        condutor.setTelefones(telefonesAtualizados);

        // Salva o condutor atualizado
        Condutor savedCondutor = condutorRepository.save(condutor);

        // Converte e retorna o DTO atualizado
        return convertToDTO(savedCondutor);
    }


}
