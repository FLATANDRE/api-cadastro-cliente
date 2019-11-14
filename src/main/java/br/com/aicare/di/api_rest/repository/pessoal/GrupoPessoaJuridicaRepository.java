/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.pessoal;

import br.com.aicare.di.api_rest.dominio.pessoal.GrupoPJ;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author FERNANDA
 */
@Repository
public interface GrupoPessoaJuridicaRepository  extends PagingAndSortingRepository<GrupoPJ, Integer>, JpaSpecificationExecutor<GrupoPJ> {

}
