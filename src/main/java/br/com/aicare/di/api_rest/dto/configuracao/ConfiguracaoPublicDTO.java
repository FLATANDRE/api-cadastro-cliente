/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.configuracao;

import br.com.aicare.di.api_rest.AICareApplication;

/**
 *
 * @author Paulo Collares
 */
public interface ConfiguracaoPublicDTO {

    public String getRegistro();
    
    public String getTitulo();

    public String getDescricao();

    default String getVersao() {
        return AICareApplication.VERSAO;
    }

}
