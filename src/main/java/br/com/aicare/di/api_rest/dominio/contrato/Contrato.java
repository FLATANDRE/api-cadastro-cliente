/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.contrato;

import br.com.aicare.di.api_rest.dominio.contrato.alocacao.AlocacaoContrato;
import br.com.aicare.di.api_rest.dominio.contrato.enums.TipoContrato;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaFisica;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Proxy;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "contrato")
@Proxy(lazy = false)
public class Contrato implements Serializable {

    @Id
    @SequenceGenerator(name = "contrato_seq", sequenceName = "contrato_seq", allocationSize = 1)
    @GeneratedValue(generator = "contrato_seq", strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pessoa", nullable = false)
    private PessoaFisica pessoaFisica;

    @Column(name = "numero")
    private String numero;

    @Column(name = "namespace_id")
    private String nameSpaceID;

    @Column(name = "tipo_contrato")
    @Enumerated(EnumType.STRING)
    private TipoContrato tipoContrato;

    @ManyToOne
    @JoinColumn(name = "contratante")
    private PessoaJuridica contratante;

    @ManyToOne
    @JoinColumn(name = "contratado")
    private PessoaJuridica contratado;

    @ManyToOne
    @JoinColumn(name = "contato_cliente")
    private PessoaFisica contatoCliente;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "contrato", cascade = CascadeType.ALL)
    private Set<CondicoesContratuais> condicoesContratuais;

    @ManyToOne
    @JoinColumn(name = "contato_interno")
    private PessoaFisica contatoInterno;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato", cascade = CascadeType.ALL)
    private Set<AlocacaoContrato> alocacoes;

    public Contrato() {
    }

    @ApiModelProperty(notes = "Identificador Único do contrato", required = true, position = -1)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ApiModelProperty(notes = "Numero do contrato", required = true)
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public String getNameSpaceID() {
        return nameSpaceID;
    }

    public void setNameSpaceID(String nameSpaceID) {
        this.nameSpaceID = nameSpaceID;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public PessoaJuridica getContratante() {
        return contratante;
    }

    public void setContratante(PessoaJuridica contratante) {
        this.contratante = contratante;
    }

    public PessoaJuridica getContratado() {
        return contratado;
    }

    public void setContratado(PessoaJuridica contratado) {
        this.contratado = contratado;
    }

    public PessoaFisica getContatoCliente() {
        return contatoCliente;
    }

    public void setContatoCliente(PessoaFisica contatoCliente) {
        this.contatoCliente = contatoCliente;
    }

    public PessoaFisica getContatoInterno() {
        return contatoInterno;
    }

    public void setContatoInterno(PessoaFisica contatoInterno) {
        this.contatoInterno = contatoInterno;
    }

    public Set<CondicoesContratuais> getCondicoesContratuais() {
        return condicoesContratuais;
    }

    public void setCondicoesContratuais(Set<CondicoesContratuais> condicoesContratuais) {
        this.condicoesContratuais = condicoesContratuais;
    }

    @JsonIgnore
    public Set<AlocacaoContrato> getAlocacoes() {
        return alocacoes;
    }

    public void setAlocacoes(Set<AlocacaoContrato> alocacoes) {
        this.alocacoes = alocacoes;
    }

}
