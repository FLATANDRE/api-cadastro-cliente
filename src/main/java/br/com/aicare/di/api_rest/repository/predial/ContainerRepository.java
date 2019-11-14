/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.predial;

import br.com.aicare.di.api_rest.dominio.predial.Container;
import br.com.aicare.di.api_rest.dto.predial.ContainerSimpleResponseDTO;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface ContainerRepository extends PagingAndSortingRepository<Container, Integer>, JpaSpecificationExecutor<Container> {

    @Query("select p.id as id, p.nome as nome from Container p where id_compartimento=:c")
    public Collection<ContainerSimpleResponseDTO> all(@Param("c") int c);

}
