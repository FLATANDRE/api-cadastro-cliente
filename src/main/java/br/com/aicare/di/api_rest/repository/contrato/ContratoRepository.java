/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.contrato;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.aicare.di.api_rest.dominio.contrato.Contrato;
import br.com.aicare.di.api_rest.dto.contrato.ContratoSimpleResponseDTO;

@Repository
public interface ContratoRepository extends PagingAndSortingRepository<Contrato, Integer>, JpaSpecificationExecutor<Contrato> {

    @Query("select c.id as id, c.numero as numero from Contrato c")
    public Collection<ContratoSimpleResponseDTO> all();

}
