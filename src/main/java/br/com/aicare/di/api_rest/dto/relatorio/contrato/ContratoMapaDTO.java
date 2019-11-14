/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.relatorio.contrato;

import br.com.aicare.di.api_rest.dominio.equipamento.ModeloEquipamento;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;

/**
 *
 * @author Paulo Collares
 */
public class ContratoMapaDTO {

    private PessoaJuridica pessoaJuridica;

    private String numeroContrato;
    private String dataContrato;
    private String dataVencimentoContrato;

    private ModeloEquipamento modeloEquipamento;
    private int quantidadeEquipamentosContratados;
    private int quantidadeEquiposContratados;

    private int equipos;
    private double preco;
    private int bombas;

    private int status;

    private String macGateway;

    private int diasParada;
    private int obrigatoriaVencida;
    private int chamadoAberto;

    private int performanceAno;
    private boolean statusRenovacaoContrato;

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public ModeloEquipamento getModeloEquipamento() {
        return modeloEquipamento;
    }

    public void setModeloEquipamento(ModeloEquipamento modeloEquipamento) {
        this.modeloEquipamento = modeloEquipamento;
    }

    public int getQuantidadeEquipamentosContratados() {
        return quantidadeEquipamentosContratados;
    }

    public void setQuantidadeEquipamentosContratados(int quantidadeEquipamentosContratados) {
        this.quantidadeEquipamentosContratados = quantidadeEquipamentosContratados;
    }

    public int getQuantidadeEquiposContratados() {
        return quantidadeEquiposContratados;
    }

    public void setQuantidadeEquiposContratados(int quantidadeEquiposContratados) {
        this.quantidadeEquiposContratados = quantidadeEquiposContratados;
    }

    public int getEquipos() {
        return equipos;
    }

    public void setEquipos(int equipos) {
        this.equipos = equipos;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getBombas() {
        return bombas;
    }

    public void setBombas(int bombas) {
        this.bombas = bombas;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusColor() {
        switch (status) {
            case -1:
                return "red";
            case 0:
                return "green";
            case 1:
                return "blue";
        }
        return "black";
    }

    public String getMacGateway() {
        return macGateway;
    }

    public void setMacGateway(String macGateway) {
        this.macGateway = macGateway;
    }

    public String getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(String dataContrato) {
        this.dataContrato = dataContrato;
    }

    public int getDiasParada() {
        return diasParada;
    }

    public void setDiasParada(int diasParada) {
        this.diasParada = diasParada;
    }

    public int getObrigatoriaVencida() {
        return obrigatoriaVencida;
    }

    public void setObrigatoriaVencida(int obrigatoriaVencida) {
        this.obrigatoriaVencida = obrigatoriaVencida;
    }

    public int getChamadoAberto() {
        return chamadoAberto;
    }

    public void setChamadoAberto(int chamadoAberto) {
        this.chamadoAberto = chamadoAberto;
    }

    public String getDataVencimentoContrato() {
        return dataVencimentoContrato;
    }

    public void setDataVencimentoContrato(String dataVencimentoContrato) {
        this.dataVencimentoContrato = dataVencimentoContrato;
    }

    public int getPerformanceAno() {
        return performanceAno;
    }

    public void setPerformanceAno(int performanceAno) {
        this.performanceAno = performanceAno;
    }

    public boolean isStatusRenovacaoContrato() {
        return statusRenovacaoContrato;
    }

    public void setStatusRenovacaoContrato(boolean statusRenovacaoContrato) {
        this.statusRenovacaoContrato = statusRenovacaoContrato;
    }

}
