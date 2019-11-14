/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.localizacao;

import br.com.aicare.di.api_rest.dominio.localizacao.Cidade;
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
public interface CidadeRepository extends PagingAndSortingRepository<Cidade, Integer> {

    public Page<Cidade> findAll(Pageable pageable);

    @Query("SELECT t FROM Cidade t "
            + "WHERE lower(nome) like %:busca% ")
    public Page<Cidade> busca(@Param("busca") String busca, Pageable pageable);

    @Query("SELECT t FROM Cidade t "
            + "WHERE lower(estado.uf) = :uf "
            + "AND lower(nome) like %:busca% ")
    public Page<Cidade> buscaPorUf(@Param("busca") String busca, @Param("uf") String uf, Pageable pageable);

    @Query("SELECT t FROM Cidade t "
            + "WHERE lower(estado.uf) = :uf ")
    public Page<Cidade> todasPorUf(@Param("uf") String uf, Pageable pageable);

}
