package br.com.fiap.parquimetro.repositories;

import br.com.fiap.parquimetro.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
