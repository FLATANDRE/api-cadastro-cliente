/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.insumo;

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
@Table(name = "tipo_insumo")
public class TipoInsumo implements Serializable {

    @Id
    @SequenceGenerator(name = "tipo_insumo_seq", sequenceName = "tipo_insumo_seq", allocationSize = 1)
    @GeneratedValue(generator = "tipo_insumo_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "descricao", columnDefinition = "text")
    private String descricao;

    @Column(name = "preco_padrao")
    private double precoPadrao;

    public TipoInsumo() {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoPadrao() {
        return precoPadrao;
    }

    public void setPrecoPadrao(double precoPadrao) {
        this.precoPadrao = precoPadrao;
    }

}
