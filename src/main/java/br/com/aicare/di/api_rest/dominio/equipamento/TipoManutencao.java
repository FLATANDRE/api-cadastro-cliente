/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.equipamento;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "tipo_manutencao")
public class TipoManutencao implements Serializable {

    @Id
    @SequenceGenerator(name = "tipo_manutencao_seq", sequenceName = "tipo_manutencao_seq", allocationSize = 1)
    @GeneratedValue(generator = "tipo_manutencao_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nome")
    private String nome;

    public TipoManutencao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
