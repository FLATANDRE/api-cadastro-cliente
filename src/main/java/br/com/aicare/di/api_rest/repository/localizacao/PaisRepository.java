/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.localizacao;

import br.com.aicare.di.api_rest.dominio.localizacao.Pais;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo
 */
@Repository
public interface PaisRepository extends PagingAndSortingRepository<Pais, Integer> {

    public Page<Pais> findAll(Pageable pageable);

    @Query("SELECT t FROM Pais t "
            + "WHERE lower(nome) like %:busca% ")
    public Page<Pais> busca(@Param("busca") String busca, Pageable pageable);

}
