package br.com.aicare.di.api_rest.dto.pessoal;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.aicare.di.api_rest.uteis.JsonDateDeserializer;

public class VinculadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String matricula;

    private String observacao;

    private Integer idNaturezaVinculo;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date inicio;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date fim;

    private Integer idPessoaFisica;

    private Integer idProfissao;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getIdNaturezaVinculo() {
        return idNaturezaVinculo;
    }

    public void setIdNaturezaVinculo(Integer idNaturezaVinculo) {
        this.idNaturezaVinculo = idNaturezaVinculo;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public Integer getIdPessoaFisica() {
        return idPessoaFisica;
    }

    public void setIdPessoaFisica(Integer idPessoaFisica) {
        this.idPessoaFisica = idPessoaFisica;
    }

    public Integer getIdProfissao() {
        return idProfissao;
    }

    public void setIdProfissao(Integer idProfissao) {
        this.idProfissao = idProfissao;
    }

}
