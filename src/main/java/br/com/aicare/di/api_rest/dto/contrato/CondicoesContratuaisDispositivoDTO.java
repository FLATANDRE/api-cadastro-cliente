/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.contrato;

import br.com.aicare.di.api_rest.dto.dispositivo.ModeloDispositivoDTO;

public class CondicoesContratuaisDispositivoDTO {

    private ModeloDispositivoDTO modeloDispositivo;

    private String uidInstanceId;

    private float precoInstalacao;

    private float precoManutencao;

    private float precoAluguel;

    private float precoVenda;

    private float precoServico;

    private int quantidadeContratada;

    public ModeloDispositivoDTO getModeloDispositivo() {
        return modeloDispositivo;
    }

    public void setModeloDispositivo(ModeloDispositivoDTO modeloDispositivo) {
        this.modeloDispositivo = modeloDispositivo;
    }

    public String getUidInstanceId() {
        return uidInstanceId;
    }

    public void setUidInstanceId(String uidInstanceId) {
        this.uidInstanceId = uidInstanceId;
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

    public int getQuantidadeContratada() {
        return quantidadeContratada;
    }

    public void setQuantidadeContratada(int quantidadeContratada) {
        this.quantidadeContratada = quantidadeContratada;
    }


}