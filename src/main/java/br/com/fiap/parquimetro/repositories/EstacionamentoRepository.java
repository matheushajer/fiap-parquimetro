package br.com.fiap.parquimetro.repositories;

import br.com.fiap.parquimetro.entities.Estacionamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long> {
}
