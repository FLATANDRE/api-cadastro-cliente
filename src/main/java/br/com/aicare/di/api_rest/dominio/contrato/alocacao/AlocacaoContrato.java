/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.contrato.alocacao;

import br.com.aicare.di.api_rest.dominio.contrato.Contrato;
import br.com.aicare.di.api_rest.uteis.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "alocacao_contrato")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AlocacaoContrato implements Serializable {

    @Id
    @SequenceGenerator(name = "alocacao_contrato_seq", sequenceName = "alocacao_contrato_seq", allocationSize = 1)
    @GeneratedValue(generator = "alocacao_contrato_seq", strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contrato", referencedColumnName = "id")
    private Contrato contrato;

    @Column(name = "associacao")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date associacao;

    @Column(name = "desassociacao", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date desassociacao;

    public AlocacaoContrato() {
    }

    public AlocacaoContrato(Contrato contrato, Date associacao) {
        this.contrato = contrato;
        this.associacao = associacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
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

}
