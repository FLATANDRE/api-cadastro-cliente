/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.contrato;

import br.com.aicare.di.api_rest.dominio.equipamento.ModeloEquipamento;
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
@Table(name = "condicoes_contratuais_equipamento")
public class CondicoesContratuaisEquipamento implements Serializable {

    @EmbeddedId
    private CondicoesContratuaisEquipamentoPK id = new CondicoesContratuaisEquipamentoPK();

    @JsonIgnore
    @JoinColumn(name = "id_condicoes_contratuais", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idCondicoesContratuais")
    private CondicoesContratuais condicoesContratuais;

    @JoinColumn(name = "id_modelo_equipamento", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idModeloEquipamento")
    private ModeloEquipamento modeloEquipamento;

    @Column(name = "preco_instalacao", columnDefinition = "float default 0")
    private float precoInstalacao;

    @Column(name = "preco_aluguel", columnDefinition = "float default 0")
    private float precoAluguel;

    @Column(name = "preco_venda", columnDefinition = "float default 0")
    private float precoVenda;

    @Column(name = "preco_servico", columnDefinition = "float default 0")
    private float precoServico;

    @Column(name = "preco_manutencao", columnDefinition = "float default 0")
    private float precoManutencao;

    @Column(name = "intervalo_manutencao_nao_obrigatoria", columnDefinition = "int default 0")
    private int intervaloManutencaoNaoObrigatoria;

    @Column(name = "intervalo_calibracao", columnDefinition = "int default 0")
    private int intervaloCalibracao;

    @Column(name = "quantidade_contratada", nullable = true)
    private int quantidadeContratada;

    @Column(name = "tempo_max_keep_alive", nullable = true)
    private int tempoMaxKeepAlive;
    
    public CondicoesContratuaisEquipamento() {
    }

    public CondicoesContratuaisEquipamentoPK getId() {
        return id;
    }

    public void setId(CondicoesContratuaisEquipamentoPK id) {
        this.id = id;
    }

    public CondicoesContratuais getCondicoesContratuais() {
        return condicoesContratuais;
    }

    public void setCondicoesContratuais(CondicoesContratuais condicoesContratuais) {
        this.condicoesContratuais = condicoesContratuais;
    }

    public ModeloEquipamento getModeloEquipamento() {
        return modeloEquipamento;
    }

    public void setModeloEquipamento(ModeloEquipamento modeloEquipamento) {
        this.modeloEquipamento = modeloEquipamento;
    }

    public float getPrecoInstalacao() {
        return precoInstalacao;
    }

    public void setPrecoInstalacao(float precoInstalacao) {
        this.precoInstalacao = precoInstalacao;
    }

    public float getPrecoAluguel() {
        return precoAluguel;
    }

    public void setPrecoAluguel(float precoAluguel) {
        this.precoAluguel = precoAluguel;
    }

    public float getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(float precoVenda) {
        this.precoVenda = precoVenda;
    }

    public float getPrecoServico() {
        return precoServico;
    }

    public void setPrecoServico(float precoServico) {
        this.precoServico = precoServico;
    }

    public float getPrecoManutencao() {
        return precoManutencao;
    }

    public void setPrecoManutencao(float precoManutencao) {
        this.precoManutencao = precoManutencao;
    }

    public int getIntervaloManutencaoNaoObrigatoria() {
        return intervaloManutencaoNaoObrigatoria;
    }

    public void setIntervaloManutencaoNaoObrigatoria(int intervaloManutencaoNaoObrigatoria) {
        this.intervaloManutencaoNaoObrigatoria = intervaloManutencaoNaoObrigatoria;
    }

    public int getIntervaloCalibracao() {
        return intervaloCalibracao;
    }

    public void setIntervaloCalibracao(int intervaloCalibracao) {
        this.intervaloCalibracao = intervaloCalibracao;
    }

    public int getQuantidadeContratada() {
        return quantidadeContratada;
    }

    public void setQuantidadeContratada(int quantidadeContratada) {
        this.quantidadeContratada = quantidadeContratada;
    }

    public int getTempoMaxKeepAlive() {
        return tempoMaxKeepAlive;
    }

    public void setTempoMaxKeepAlive(int tempoMaxKeepAlive) {
        this.tempoMaxKeepAlive = tempoMaxKeepAlive;
    }

}
