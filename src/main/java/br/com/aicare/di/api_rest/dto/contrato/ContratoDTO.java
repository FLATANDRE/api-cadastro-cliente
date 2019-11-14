/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.contrato;

import java.util.List;
import br.com.aicare.di.api_rest.dto.pessoal.PessoaFisicaDTO;
import br.com.aicare.di.api_rest.dto.pessoal.PessoaJuridicaDTO;

public class ContratoDTO {

    private int id;
    private String numero;
    private PessoaJuridicaDTO contratante;
    private PessoaJuridicaDTO contratado;
    private PessoaFisicaDTO contatoCliente;
    private PessoaFisicaDTO contatoInterno;
    private String tipoContrato;
    private boolean vinculadoHolding;
    
    private List<CondicoesContratuaisDTO> listaCondicoesContratuais;
   
    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public List<CondicoesContratuaisDTO> getListaCondicoesContratuais() {
        return listaCondicoesContratuais;
    }

    public void setListaCondicoesContratuais(List<CondicoesContratuaisDTO> listaCondicoesContratuais) {
        this.listaCondicoesContratuais = listaCondicoesContratuais;
    }

    public boolean isVinculadoHolding() {
        return vinculadoHolding;
    }

    public void setVinculadoHolding(boolean vinculadoHolding) {
        this.vinculadoHolding = vinculadoHolding;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public PessoaJuridicaDTO getContratante() {
        return contratante;
    }

    public void setContratante(PessoaJuridicaDTO contratante) {
        this.contratante = contratante;
    }

    public PessoaJuridicaDTO getContratado() {
        return contratado;
    }

    public void setContratado(PessoaJuridicaDTO contratado) {
        this.contratado = contratado;
    }

    public PessoaFisicaDTO getContatoCliente() {
        return contatoCliente;
    }

    public void setContatoCliente(PessoaFisicaDTO contatoCliente) {
        this.contatoCliente = contatoCliente;
    }

    public PessoaFisicaDTO getContatoInterno() {
        return contatoInterno;
    }

    public void setContatoInterno(PessoaFisicaDTO contatoInterno) {
        this.contatoInterno = contatoInterno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
}