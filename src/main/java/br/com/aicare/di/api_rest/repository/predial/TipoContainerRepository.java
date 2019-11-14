/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.predial;

import br.com.aicare.di.api_rest.dominio.predial.TipoContainer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface TipoContainerRepository extends PagingAndSortingRepository<TipoContainer, Integer> {

}
