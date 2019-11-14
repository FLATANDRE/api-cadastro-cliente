/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.manutencao;

import br.com.aicare.di.api_rest.dominio.equipamento.Manutencao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface ManutencaoRepository extends PagingAndSortingRepository<Manutencao, Integer>, JpaSpecificationExecutor<Manutencao> {

}
