/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.fatura;

import java.util.List;

import br.com.aicare.di.api_rest.dto.contrato.ContratoDTO;
import br.com.aicare.di.api_rest.dto.pessoal.PessoaJuridicaDTO;
import br.com.aicare.di.api_rest.dto.fatura.itens.insumo.ItemFaturaInsumoDTO;
import br.com.aicare.di.api_rest.dto.fatura.itens.modelo_dispositivo.ItemFaturaModeloDispositivoDTO;
import br.com.aicare.di.api_rest.dto.fatura.itens.modelo_equipamento.ItemFaturaModeloEquipamentoDTO;


public class FaturaDTO {

    private int id;
    private String numeroNotaFiscal;
    private String data;
    private String descricao;
    private String natureza;
    private PessoaJuridicaDTO pessoaJuridica;
    private ContratoDTO contrato;
    private List<ItemFaturaModeloDispositivoDTO> itensModeloDispositivo;
    private List<ItemFaturaModeloEquipamentoDTO> itensModeloEquipamento;
    private List<ItemFaturaInsumoDTO> itensInsumo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public PessoaJuridicaDTO getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridicaDTO pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public ContratoDTO getContrato() {
        return contrato;
    }

    public void setContrato(ContratoDTO contrato) {
        this.contrato = contrato;
    }

    public List<ItemFaturaModeloDispositivoDTO> getItensModeloDispositivo() {
        return itensModeloDispositivo;
    }

    public void setItensModeloDispositivo(List<ItemFaturaModeloDispositivoDTO> itensModeloDispositivo) {
        this.itensModeloDispositivo = itensModeloDispositivo;
    }

    public List<ItemFaturaModeloEquipamentoDTO> getItensModeloEquipamento() {
        return itensModeloEquipamento;
    }

    public void setItensModeloEquipamento(List<ItemFaturaModeloEquipamentoDTO> itensModeloEquipamento) {
        this.itensModeloEquipamento = itensModeloEquipamento;
    }

    public List<ItemFaturaInsumoDTO> getItensInsumo() {
        return itensInsumo;
    }

    public void setItensInsumo(List<ItemFaturaInsumoDTO> itensInsumo) {
        this.itensInsumo = itensInsumo;
    }

    
}