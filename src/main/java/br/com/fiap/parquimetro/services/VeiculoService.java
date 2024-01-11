package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.entities.Veiculo;
import br.com.fiap.parquimetro.repositories.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository repository;

    public Veiculo save(Veiculo veiculo) {
        return this.repository.save(veiculo);
    }
}
