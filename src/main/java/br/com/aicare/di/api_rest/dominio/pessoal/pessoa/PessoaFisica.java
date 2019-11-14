/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.pessoa;

import br.com.aicare.di.api_rest.dominio.contrato.Contrato;
import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.Colaborador;
import br.com.aicare.di.api_rest.dominio.pessoal.representante.RepresentantePessoaFisica;
import br.com.aicare.di.api_rest.uteis.CPF;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Paulo Collares
 */
@Entity
@Table(name = "pessoa_fisica")
public class PessoaFisica extends Pessoa {

    private static final long serialVersionUID = 1L;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "rg")
    private String rg;

    @Column(name = "data_nascimento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascimento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoaFisica")
    private Set<Colaborador> colaboradores;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contatoCliente", targetEntity = Contrato.class, cascade = CascadeType.ALL)
    private Set<Contrato> contatosClientes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contatoInterno", targetEntity = Contrato.class, cascade = CascadeType.ALL)
    private Set<Contrato> contatosInternos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoaJuridica", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PessoaFisicaPessoaJuridica> contatos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "representante", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RepresentantePessoaFisica> representantes;

    public PessoaFisica() {
    }

    @ApiModelProperty(notes = "CPF da pessoa fisica", example = "111.222.333-44", required = true)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Transient
    public String getCpfFormatado() {
        CPF cpf = new CPF(getCpf());
        return cpf.toString();
    }

    @ApiModelProperty(notes = "RG da pessoa fisica", required = true)
    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @ApiModelProperty(notes = "Data de nascimento da pessoa fisica", dataType = "Data", example = "01/05/2015", required = true)
    @Transient
    public String getDataNascimentoFormatada() {
        if (getDataNascimento() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(getDataNascimento());
        }
        return null;
    }

    @JsonIgnore
    public Set<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(Set<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    @JsonIgnore
    public Set<Contrato> getContatosClientes() {
        return contatosClientes;
    }

    public void setContatosClientes(Set<Contrato> contatosClientes) {
        this.contatosClientes = contatosClientes;
    }

    @JsonIgnore
    public Set<Contrato> getContatosInternos() {
        return contatosInternos;
    }

    public void setContatosInternos(Set<Contrato> contatosInternos) {
        this.contatosInternos = contatosInternos;
    }

    @JsonIgnore
    public Set<PessoaFisicaPessoaJuridica> getContatos() {
        return contatos;
    }

    public void setContatos(Set<PessoaFisicaPessoaJuridica> contatos) {
        this.contatos = contatos;
    }

    @JsonIgnore
    public Set<RepresentantePessoaFisica> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(Set<RepresentantePessoaFisica> representantes) {
        this.representantes = representantes;
    }

}
