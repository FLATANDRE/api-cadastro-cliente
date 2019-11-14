/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.dispositivo;

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
@Table(name = "fabricante_dispositivo")
public class FabricanteDispositivo {

    @Id
    @SequenceGenerator(name = "fabricante_dispositivo_seq", sequenceName = "fabricante_dispositivo_seq", allocationSize = 1)
    @GeneratedValue(generator = "fabricante_dispositivo_seq", strategy = GenerationType.AUTO)
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
