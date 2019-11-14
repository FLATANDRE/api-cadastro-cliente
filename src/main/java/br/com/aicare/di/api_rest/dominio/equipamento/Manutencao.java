/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.equipamento;

import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaFisica;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "manutencao")
public class Manutencao implements Serializable {

    @Id
    @SequenceGenerator(name = "manutencao_seq", sequenceName = "manutencao_seq", allocationSize = 1)
    @GeneratedValue(generator = "manutencao_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "data_manutencao")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataManutencao;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "id_tipo_manutencao")
    private TipoManutencao tipoManutencao;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_equipamento", nullable = false)
    private Equipamento equipamento;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pessoa", nullable = false)
    private PessoaFisica responsavel;

    public Manutencao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Date getDataManutencao() {
        return dataManutencao;
    }

    public void setDataManutencao(Date dataManutencao) {
        this.dataManutencao = dataManutencao;
    }

    @Transient
    public String getDataManutencaoFormatada() {
        if (getDataManutencao() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(getDataManutencao());
        }
        return null;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public TipoManutencao getTipoManutencao() {
        return tipoManutencao;
    }

    public void setTipoManutencao(TipoManutencao tipoManutencao) {
        this.tipoManutencao = tipoManutencao;
    }

    public PessoaFisica getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(PessoaFisica responsavel) {
        this.responsavel = responsavel;
    }

}
