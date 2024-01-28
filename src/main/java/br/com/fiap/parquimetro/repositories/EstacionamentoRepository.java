package br.com.fiap.parquimetro.repositories;

import br.com.fiap.parquimetro.entities.Estacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long> {

    @Query("SELECT e.id FROM Estacionamento e")
    List<Long> findAllId(); // Corrected method name

}
