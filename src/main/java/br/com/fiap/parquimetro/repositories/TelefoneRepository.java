package br.com.fiap.parquimetro.repositories;

import br.com.fiap.parquimetro.entities.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
