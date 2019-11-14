/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.contrato;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.aicare.di.api_rest.dominio.contrato.LocalPlanejado;
import br.com.aicare.di.api_rest.dominio.contrato.LocalPlanejadoPK;

@Repository
public interface LocalPlanejadoRepository extends PagingAndSortingRepository<LocalPlanejado, LocalPlanejadoPK>, JpaSpecificationExecutor<LocalPlanejado> {

    
}