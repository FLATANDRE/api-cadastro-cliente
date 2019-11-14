/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.contrato;

import br.com.aicare.di.api_rest.dominio.dispositivo.ModeloDispositivo;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "condicoes_contratuais_dispositivo")
public class CondicoesContratuaisDispositivo implements Serializable {

    @EmbeddedId
    private CondicoesContratuaisDispositivoPK id = new CondicoesContratuaisDispositivoPK();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_condicoes_contratuais")
    @MapsId("idCondicoesContratuais")
    private CondicoesContratuais condicoesContratuais;

    @ManyToOne
    @JoinColumn(name = "id_modelo_dispositivo")
    @MapsId("idModeloDispositivo")
    private ModeloDispositivo modeloDispositivo;

    @Column(name = "UID_instance_ID")
    private String uidInstanceId;

    @Column(name = "preco_instalacao", columnDefinition = "float default 0")
    private float precoInstalacao;

    @Column(name = "preco_munutecao", columnDefinition = "float default 0")
    private float precoManutencao;

    @Column(name = "preco_aluguel", columnDefinition = "float default 0")
    private float precoAluguel;

    @Column(name = "preco_venda", columnDefinition = "float default 0")
    private float precoVenda;

    @Column(name = "preco_servico", columnDefinition = "float default 0")
    private float precoServico;

    @Column(name = "quantidade_contratada", nullable = true)
    private int quantidadeContratada;

    public CondicoesContratuaisDispositivo() {
    }

    public CondicoesContratuaisDispositivoPK getId() {
        return id;
    }

    public void setId(CondicoesContratuaisDispositivoPK id) {
        this.id = id;
    }

    public CondicoesContratuais getCondicoesContratuais() {
        return condicoesContratuais;
    }

    public void setCondicoesContratuais(CondicoesContratuais condicoesContratuais) {
        this.condicoesContratuais = condicoesContratuais;
    }

    public ModeloDispositivo getModeloDispositivo() {
        ModeloDispositivo modeloSimplificado = new ModeloDispositivo();
        modeloSimplificado.setDesconhecido(modeloDispositivo.isDesconhecido());
        modeloSimplificado.setFabricante(modeloDispositivo.getFabricante());
        modeloSimplificado.setId(modeloDispositivo.getId());
        modeloSimplificado.setIntervaloManutencaoObrigatoria(modeloDispositivo.getIntervaloManutencaoObrigatoria());
        modeloSimplificado.setNome(modeloDispositivo.getNome());
        modeloSimplificado.setTensaoMaximaBateria(modeloDispositivo.getTensaoMaximaBateria());
        modeloSimplificado.setTensaoMinimaBateria(modeloDispositivo.getTensaoMinimaBateria());
        modeloSimplificado.setDispositivos(modeloDispositivo.getDispositivos());
        
        return modeloSimplificado;
    }

    public void setModeloDispositivo(ModeloDispositivo modeloDispositivo) {
        this.modeloDispositivo = modeloDispositivo;
    }

    public float getPrecoInstalacao() {
        return precoInstalacao;
    }

    public void setPrecoInstalacao(float precoInstalacao) {
        this.precoInstalacao = precoInstalacao;
    }

    public float getPrecoManutencao() {
        return precoManutencao;
    }

    public void setPrecoManutencao(float precoManutencao) {
        this.precoManutencao = precoManutencao;
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

    public String getUidInstanceId() {
        return uidInstanceId;
    }

    public void setUidInstanceId(String uidInstanceId) {
        this.uidInstanceId = uidInstanceId;
    }

    public int getQuantidadeContratada() {
        return quantidadeContratada;
    }

    public void setQuantidadeContratada(int quantidadeContratada) {
        this.quantidadeContratada = quantidadeContratada;
    }  

}
