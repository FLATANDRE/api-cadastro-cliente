/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.colaborador;

import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.Colaborador;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author FERNANDA
 */
@Entity
@Table(name = "vinculado")
public class Vinculado extends Colaborador {

    private static final long serialVersionUID = 1L;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_natureza_vinculo", referencedColumnName = "id")
    private NaturezaVinculo natureza;

    public Vinculado() {
    }

    @ApiModelProperty(notes = "Matricula do vinculado", required = true)
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @ApiModelProperty(notes = "Natureza do vinculado", required = true)
    public NaturezaVinculo getNatureza() {
        return natureza;
    }

    public void setNatureza(NaturezaVinculo natureza) {
        this.natureza = natureza;
    }

    @ApiModelProperty(notes = "Observacao do vinculado", required = true)
    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
