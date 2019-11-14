/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.ordem_producao;

import br.com.aicare.di.api_rest.dominio.ordem_producao.OrdemProducao;
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
public interface OrdemProducaoRepository extends PagingAndSortingRepository<OrdemProducao, Integer>, JpaSpecificationExecutor<OrdemProducao> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM OrdemProducao t WHERE numero = :numero")
    public boolean numeroExistente(@Param("numero") String numero);

}
