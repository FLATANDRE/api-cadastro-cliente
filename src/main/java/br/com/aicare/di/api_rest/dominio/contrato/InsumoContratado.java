/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.contrato;

import br.com.aicare.di.api_rest.dominio.insumo.TipoInsumo;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "condicoes_contratuais_insumos_cobertos")
public class InsumoContratado implements Serializable {

    @EmbeddedId
    private InsumoContratadoPK id = new InsumoContratadoPK();

    @JsonIgnore
    @JoinColumn(name = "id_condicoes_contratuais", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idCondicoesContratuais")
    private CondicoesContratuais condicoesContratuais;

    @JoinColumn(name = "id_tipo_insumo", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idTipoInsumo")
    private TipoInsumo tipoInsumo;

    @Column(name = "quantidade_minima")
    private int quantidadeMinima;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco_contratado")
    private float precoContratado;

    public InsumoContratado() {
    }

    public InsumoContratado(CondicoesContratuais condicoesContratuais, TipoInsumo tipoInsumo, int quantidadeMinima, String descricao, float precoContratado) {
        this.condicoesContratuais = condicoesContratuais;
        this.tipoInsumo = tipoInsumo;
        this.quantidadeMinima = quantidadeMinima;
        this.descricao = descricao;
        this.precoContratado = precoContratado;
    }

    public InsumoContratadoPK getId() {
        return id;
    }

    public void setId(InsumoContratadoPK id) {
        this.id = id;
    }

    public CondicoesContratuais getCondicoesContratuais() {
        return condicoesContratuais;
    }

    public void setCondicoesContratuais(CondicoesContratuais condicoesContratuais) {
        this.condicoesContratuais = condicoesContratuais;
    }

    public TipoInsumo getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(TipoInsumo tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPrecoContratado() {
        return precoContratado;
    }

    public void setPrecoContratado(float precoContratado) {
        this.precoContratado = precoContratado;
    }

}
