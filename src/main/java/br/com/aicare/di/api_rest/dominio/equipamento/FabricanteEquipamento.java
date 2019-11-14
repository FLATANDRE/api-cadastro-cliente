/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.equipamento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author pcollares
 */
@Entity
@Table(name = "fabricante_equipamento")
public class FabricanteEquipamento {

    @Id
    @SequenceGenerator(name = "fabricante_equipamento_seq", sequenceName = "fabricante_equipamento_seq", allocationSize = 1)
    @GeneratedValue(generator = "fabricante_equipamento_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nome")
    private String nome;

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
