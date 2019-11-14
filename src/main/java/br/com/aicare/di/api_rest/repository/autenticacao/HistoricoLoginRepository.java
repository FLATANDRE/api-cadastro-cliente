/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.autenticacao;

import br.com.aicare.di.api_rest.dominio.autenticacao.HistoricoLogin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface HistoricoLoginRepository extends PagingAndSortingRepository<HistoricoLogin, Integer> {

    public Page<HistoricoLogin> findAll(Pageable pageable);

}
