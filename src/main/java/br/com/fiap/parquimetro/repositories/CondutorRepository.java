package br.com.fiap.parquimetro.repositories;

import br.com.fiap.parquimetro.entities.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CondutorRepository extends JpaRepository<Condutor, Long> {

}
