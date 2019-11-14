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
@Table(name = "tipo_dispositivo")
public class TipoDispositivo {

    @Id
    @SequenceGenerator(name = "tipo_dispositivo_seq", sequenceName = "tipo_dispositivo_seq", allocationSize = 1)
    @GeneratedValue(generator = "tipo_dispositivo_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "associavel_equipamento", columnDefinition = "boolean default false")
    private boolean associavelEquipamento;

    @Column(name = "associavel_localizacao", columnDefinition = "boolean default false")
    private boolean associavelLocalizacao;

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

    public boolean isAssociavelEquipamento() {
        return associavelEquipamento;
    }

    public void setAssociavelEquipamento(boolean associavelEquipamento) {
        this.associavelEquipamento = associavelEquipamento;
    }

    public boolean isAssociavelLocalizacao() {
        return associavelLocalizacao;
    }

    public void setAssociavelLocalizacao(boolean associavelLocalizacao) {
        this.associavelLocalizacao = associavelLocalizacao;
    }

}
