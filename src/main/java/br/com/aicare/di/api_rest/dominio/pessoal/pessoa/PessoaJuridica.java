/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.pessoa;

import br.com.aicare.di.api_rest.dominio.contrato.Contrato;
import br.com.aicare.di.api_rest.dominio.fatura.Fatura;
import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.ColaboradorPessoaJuridica;
import br.com.aicare.di.api_rest.dominio.pessoal.representante.RepresentantePessoaJuridica;
import br.com.aicare.di.api_rest.uteis.CNPJ;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Paulo Collares
 */
@Entity
@Table(name = "pessoa_juridica")
public class PessoaJuridica extends Pessoa {

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ColaboradorPessoaJuridica> colaboradores;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoaJuridica", targetEntity = Fatura.class, cascade = CascadeType.ALL)
    private Set<Fatura> faturas;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contratante", targetEntity = Contrato.class, cascade = CascadeType.ALL)
    private Set<Contrato> contratantes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contratado", targetEntity = Contrato.class, cascade = CascadeType.ALL)
    private Set<Contrato> contratados;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoaFisica", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PessoaFisicaPessoaJuridica> contatos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "representante", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RepresentantePessoaJuridica> representantes;

    public PessoaJuridica() {
    }

    @ApiModelProperty(notes = "CNPJ da pessoa juridica", example = "11.222.333/4444-55", required = true)
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Transient
    public String getCnpjFormatado() {
        CNPJ cnpj = new CNPJ(getCnpj());
        return cnpj.toString();
    }

    @ApiModelProperty(notes = "Nome fantasia da pessoa juridica", required = true)
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    @ApiModelProperty(notes = "Inscrição Estadual da pessoa juridica", required = true)
    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    @JsonIgnore
    public Set<ColaboradorPessoaJuridica> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(Set<ColaboradorPessoaJuridica> colaboradores) {
        this.colaboradores = colaboradores;
    }

    @JsonIgnore
    public Set<Fatura> getFaturas() {
        return faturas;
    }

    public void setFaturas(Set<Fatura> faturas) {
        this.faturas = faturas;
    }

    @JsonIgnore
    public Set<Contrato> getContratantes() {
        return contratantes;
    }

    public void setContratantes(Set<Contrato> contratantes) {
        this.contratantes = contratantes;
    }

    @JsonIgnore
    public Set<Contrato> getContratados() {
        return contratados;
    }

    public void setContratados(Set<Contrato> contratados) {
        this.contratados = contratados;
    }

    @JsonIgnore
    public Set<PessoaFisicaPessoaJuridica> getContatos() {
        return contatos;
    }

    public void setContatos(Set<PessoaFisicaPessoaJuridica> contatos) {
        this.contatos = contatos;
    }

    @JsonIgnore
    public Set<RepresentantePessoaJuridica> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(Set<RepresentantePessoaJuridica> representantes) {
        this.representantes = representantes;
    }
}
