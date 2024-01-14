package br.com.fiap.parquimetro.repositories;

import br.com.fiap.parquimetro.entities.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CondutorRepository extends JpaRepository<Condutor, Long> {

    @Query("SELECT c FROM Condutor c JOIN FETCH c.telefones JOIN FETCH c.enderecos JOIN FETCH c.formaDePagamento JOIN FETCH c.veiculos")
    List<Condutor> findAllWithAssociations();

}
