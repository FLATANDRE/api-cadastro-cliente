/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.predial;

import br.com.aicare.di.api_rest.dominio.predial.Compartimento;
import br.com.aicare.di.api_rest.dto.predial.CompartimentoSimpleResponseDTO;
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
public interface CompartimentoRepository extends PagingAndSortingRepository<Compartimento, Integer>, JpaSpecificationExecutor<Compartimento> {

    @Query("select p.id as id, p.nome as nome from Compartimento p where id_localizacao_fisica=:lf")
    public Collection<CompartimentoSimpleResponseDTO> all(@Param("lf") int lf);

}
