/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.contrato;

import br.com.aicare.di.api_rest.dto.predial.LocalizacaoFisicaDTO;

public class LocalizacaoObjetoDTO {
    
    private LocalizacaoFisicaDTO localizacaoFisica;
    private int quantidadePlanejada;

    public LocalizacaoFisicaDTO getLocalizacaoFisica() {
        return localizacaoFisica;
    }

    public void setLocalizacaoFisica(LocalizacaoFisicaDTO localizacaoFisica) {
        this.localizacaoFisica = localizacaoFisica;
    }

    public int getQuantidadePlanejada() {
        return quantidadePlanejada;
    }

    public void setQuantidadePlanejada(int quantidadePlanejada) {
        this.quantidadePlanejada = quantidadePlanejada;
    }

    
    
    
}