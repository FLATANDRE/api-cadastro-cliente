/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.ordem_producao;

import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Paulo Collares
 */
@Entity
@Table(name = "ordem_producao")
public class OrdemProducao implements Serializable {

    @Id
    @SequenceGenerator(name = "ordem_producao_seq", sequenceName = "ordem_producao_seq", allocationSize = 1)
    @GeneratedValue(generator = "ordem_producao_seq", strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "data")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fornecedor", nullable = false)
    private PessoaJuridica fornecedor;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ordemProducao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrdemProducaoModeloDispositivo> itens;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getData() {
        return data;
    }

    @ApiModelProperty(notes = "Data formatada", dataType = "Data", example = "01/05/2015", required = true)
    @Transient
    public String getDataFormatada() {
        if (getData() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(getData());
        }
        return null;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<OrdemProducaoModeloDispositivo> getItens() {
        return itens;
    }

    public void setItens(Set<OrdemProducaoModeloDispositivo> itens) {
        this.itens = itens;
    }

    public PessoaJuridica getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(PessoaJuridica fornecedor) {
        this.fornecedor = fornecedor;
    }

}
