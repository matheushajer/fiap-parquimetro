package br.com.fiap.parquimetro.repositories;

import br.com.fiap.parquimetro.entities.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReciboRepository extends JpaRepository<Recibo, Long> {
}
