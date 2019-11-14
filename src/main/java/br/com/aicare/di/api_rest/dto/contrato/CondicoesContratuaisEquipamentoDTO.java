/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.contrato;

import br.com.aicare.di.api_rest.dto.equipamento.ModeloEquipamentoDTO;

public class CondicoesContratuaisEquipamentoDTO {

    private ModeloEquipamentoDTO modeloEquipamento;

    private float precoInstalacao;

    private float precoAluguel;

    private float precoVenda;

    private float precoServico;

    private float precoManutencao;

    private int intervaloManutencaoNaoObrigatoria;

    private int intervaloCalibracao;

    private int quantidadeContratada;

    private int tempoMaxKeepAlive;

    public ModeloEquipamentoDTO getModeloEquipamento() {
        return modeloEquipamento;
    }

    public void setModeloEquipamento(ModeloEquipamentoDTO modeloEquipamento) {
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