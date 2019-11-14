/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.dispositivo;

import br.com.aicare.di.api_rest.dominio.dispositivo.FabricanteDispositivo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface FabricanteDispositivoRepository extends PagingAndSortingRepository<FabricanteDispositivo, Integer> {

}
