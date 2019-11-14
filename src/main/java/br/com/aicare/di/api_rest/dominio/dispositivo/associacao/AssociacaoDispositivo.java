/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.dispositivo.associacao;

import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import br.com.aicare.di.api_rest.uteis.Data;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Paulo Collares
 */
@Entity
@Table(name = "associacao_dispositivo")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AssociacaoDispositivo implements Serializable {

    @Id
    @SequenceGenerator(name = "associacao_dispositivo_seq", sequenceName = "associacao_dispositivo_seq", allocationSize = 1)
    @GeneratedValue(generator = "associacao_dispositivo_seq", strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dispositivo", referencedColumnName = "id")
    private Dispositivo dispositivo;

    @Column(name = "associacao")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date associacao;

    @Column(name = "desassociacao", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date desassociacao;

    public AssociacaoDispositivo() {
    }

    public AssociacaoDispositivo(Dispositivo dispositivo, Date associacao) {
        this.dispositivo = dispositivo;
        this.associacao = associacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Date getAssociacao() {
        return associacao;
    }

    public void setAssociacao(Date associacao) {
        this.associacao = associacao;
    }

    public Date getDesassociacao() {
        return desassociacao;
    }

    public void setDesassociacao(Date desassociacao) {
        this.desassociacao = desassociacao;
    }

    @Transient
    public String getAssociacaoFormatada() {
        if (associacao != null) {
            Data data = new Data(associacao);
            return data.toString();
        }
        return null;
    }

    @Transient
    public String getDesassociacaoFormatada() {
        if (desassociacao != null) {
            Data data = new Data(desassociacao);
            return data.toString();
        }
        return null;
    }

    @Transient
    public abstract String getTipoAssociacao();

    @Transient
    public abstract String getTipoAssociacaoNome();

    @Transient
    public abstract String getDescricaoAssociacao();

}
