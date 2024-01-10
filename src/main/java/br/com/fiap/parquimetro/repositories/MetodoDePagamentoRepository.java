package br.com.fiap.parquimetro.repositories;

import br.com.fiap.parquimetro.entities.MetodoDePagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetodoDePagamentoRepository extends JpaRepository<MetodoDePagamento, Long> {
}
