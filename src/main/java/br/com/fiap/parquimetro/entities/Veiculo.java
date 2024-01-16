package br.com.fiap.parquimetro.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String modelo;

    private String cor;

    private String placa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condutor_id")
    private Condutor condutor;

    public Veiculo(String modelo, String cor, String placa) {
        this.modelo = modelo;
        this.cor = cor;
        this.placa = placa;
    }

    // ************************************************
    // Seleção Getter e Setter
    // ************************************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Condutor getCondutor() {
        return condutor;
    }

    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }
}
