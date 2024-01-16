package br.com.fiap.parquimetro.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_telefones")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int ddi;
    private int ddd;
    private int telefone;
    private boolean isTelefonePrincipal;

    @ManyToOne
    @JoinColumn(name = "condutor_id")
    private Condutor condutor;

    public Telefone(int ddi, int ddd, int numeroTelefone, boolean isTelefonePrincipal) {
        this.ddi = ddi;
        this.ddd = ddd;
        this.telefone = numeroTelefone;
        this.isTelefonePrincipal = isTelefonePrincipal;
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

    public int getDdi() {
        return ddi;
    }

    public void setDdi(int ddi) {
        this.ddi = ddi;
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public boolean isTelefonePrincipal() {
        return isTelefonePrincipal;
    }

    public void setTelefonePrincipal(boolean telefonePrincipal) {
        isTelefonePrincipal = telefonePrincipal;
    }

    public Condutor getCondutor() {
        return condutor;
    }

    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }
}
