/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.pessoal;

import br.com.aicare.di.api_rest.uteis.CPF;

/**
 *
 * @author Paulo Collares
 */
public interface PessoaFisicaSimpleResponseDTO {

    public Integer getId();

    public String getNome();

    public String getCpf();
    
    public default String getCpfFormatado(){
        CPF cpf = new CPF(getCpf());
        return cpf.toString();
    }

}
