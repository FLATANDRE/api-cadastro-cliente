/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.pessoal;

import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.Profissao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author FERNANDA
 */
@Repository
public interface ProfissaoRepository extends PagingAndSortingRepository<Profissao, Integer> {

    @Query("SELECT p FROM Profissao p "
            + "WHERE lower(nome) like %:busca% ")
    public Page<Profissao> busca(@Param("busca") String busca, Pageable pageable);

}
