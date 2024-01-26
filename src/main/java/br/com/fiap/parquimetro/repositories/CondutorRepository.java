package br.com.fiap.parquimetro.repositories;

import br.com.fiap.parquimetro.dto.CondutorDTO;
import br.com.fiap.parquimetro.entities.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface CondutorRepository extends JpaRepository<Condutor, Long> {

    public Optional<Condutor> findByCpf(String cpf);


}
