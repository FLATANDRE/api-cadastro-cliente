/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.contrato;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.aicare.di.api_rest.dominio.contrato.InsumoContratado;
import br.com.aicare.di.api_rest.dominio.contrato.InsumoContratadoPK;

@Repository
public interface InsumoContratadoRepository extends PagingAndSortingRepository<InsumoContratado, InsumoContratadoPK>, JpaSpecificationExecutor<InsumoContratado> {

    
}