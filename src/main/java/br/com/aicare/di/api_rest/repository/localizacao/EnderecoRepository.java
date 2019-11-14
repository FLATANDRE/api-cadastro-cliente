/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.localizacao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.aicare.di.api_rest.dominio.localizacao.Endereco;

/**
 *
 * @author FERNANDA
 */
@Repository
public interface EnderecoRepository extends PagingAndSortingRepository<Endereco, Integer> {

}
