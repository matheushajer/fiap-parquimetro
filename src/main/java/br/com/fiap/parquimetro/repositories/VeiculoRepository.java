package br.com.fiap.parquimetro.repositories;

import br.com.fiap.parquimetro.entities.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}