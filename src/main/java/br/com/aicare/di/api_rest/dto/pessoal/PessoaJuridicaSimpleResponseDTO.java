/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.pessoal;

import br.com.aicare.di.api_rest.uteis.CNPJ;

/**
 *
 * @author Paulo Collares
 */
public interface PessoaJuridicaSimpleResponseDTO {

    public Integer getId();

    public String getNome();

    public String getCnpj();

    public default String getCnpjFormatado() {
        CNPJ cnpj = new CNPJ(getCnpj());
        return cnpj.toString();
    }
}
