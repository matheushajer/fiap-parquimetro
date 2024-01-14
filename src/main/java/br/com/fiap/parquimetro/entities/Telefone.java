package br.com.fiap.parquimetro.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
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
}
