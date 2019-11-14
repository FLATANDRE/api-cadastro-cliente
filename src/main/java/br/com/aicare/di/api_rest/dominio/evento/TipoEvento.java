/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.evento;

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
 * @author Paulo Collares
 */
@Entity
@Table(name = "tipo_evento")
public class TipoEvento implements Serializable {

    @Id
    @SequenceGenerator(name = "tipo_evento_seq", sequenceName = "tipo_evento_seq", allocationSize = 1)
    @GeneratedValue(generator = "tipo_evento_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "quantidade_maxima_por_dispositivo", nullable = true, columnDefinition = "int default 100")
    private int quantidadeMaximaPorDispositivo;

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

    public int getQuantidadeMaximaPorDispositivo() {
        return quantidadeMaximaPorDispositivo;
    }

    public void setQuantidadeMaximaPorDispositivo(int quantidadeMaximaPorDispositivo) {
        this.quantidadeMaximaPorDispositivo = quantidadeMaximaPorDispositivo;
    }

}
