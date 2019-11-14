/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.colaborador;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaFisica;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "colaborador")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Colaborador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "colaborador_seq", sequenceName = "colaborador_seq", allocationSize = 1)
    @GeneratedValue(generator = "colaborador_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "inicio")
    @Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date inicio;

    @Column(name = "fim", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date fim;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoaJuridica", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ColaboradorPessoaJuridica> pessoasJuridicas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pessoa_fisica", referencedColumnName = "id")
    private PessoaFisica pessoaFisica;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_profissao", referencedColumnName = "id")
    private Profissao profissao;

    public Colaborador() {
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    @ApiModelProperty(notes = "Data de Inicio do vinculo", dataType = "Data", example = "2019-05-01", required = true)
    public Date getInicio() {
	return inicio;
    }

    public void setInicio(Date inicio) {
	this.inicio = inicio;
    }

    @ApiModelProperty(notes = "Data final do vinculo", dataType = "Data", example = "2019-05-01", required = true)
    public Date getFim() {
	return fim;
    }

    public void setFim(Date fim) {
	this.fim = fim;
    }

    public Set<ColaboradorPessoaJuridica> getPessoasJuridicas() {
	return pessoasJuridicas;
    }

    public void setPessoasJuridicas(Set<ColaboradorPessoaJuridica> pessoasJuridicas) {
	this.pessoasJuridicas = pessoasJuridicas;
    }

    public PessoaFisica getPessoaFisica() {
	return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
	this.pessoaFisica = pessoaFisica;
    }

    public Profissao getProfissao() {
	return profissao;
    }

    public void setProfissao(Profissao profissao) {
	this.profissao = profissao;
    }

}
