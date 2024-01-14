package br.com.fiap.parquimetro.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_condutores")
public class Condutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    String cpf;
    String email;

    @OneToMany(mappedBy = "condutor", cascade = CascadeType.ALL)
    List<Telefone> telefones;

    @OneToMany(mappedBy = "condutor", cascade = CascadeType.ALL)
    List<Endereco> enderecos;

    @OneToMany(mappedBy = "condutor", cascade = CascadeType.ALL)
    private List<MetodoDePagamento> formaDePagamento;

    @OneToMany(mappedBy = "condutor", cascade = CascadeType.ALL)
    private List<Veiculo> veiculos;


    public Condutor(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }
}
