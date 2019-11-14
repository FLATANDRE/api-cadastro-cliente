/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.localizacao;

import br.com.aicare.di.api_rest.dominio.localizacao.Estado;
import java.util.Optional;
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
public interface EstadoRepository extends PagingAndSortingRepository<Estado, Integer> {

    public Optional<Estado> findByUf(String uf);

    public Page<Estado> findAll(Pageable pageable);

    @Query("SELECT t FROM Estado t "
            + "WHERE lower(nome) like %:busca% "
            + "OR lower(uf) like %:busca% ")
    public Page<Estado> busca(@Param("busca") String busca, Pageable pageable);

    @Query("SELECT t FROM Estado t "
            + "WHERE lower(pais.sigla) = :sigla "
            + "AND lower(nome) like %:busca% ")
    public Page<Estado> buscaPorPais(@Param("busca") String busca, @Param("sigla") String sigla, Pageable pageable);

    @Query("SELECT t FROM Estado t "
            + "WHERE lower(pais.sigla) = :sigla ")
    public Page<Estado> todasPorPais(@Param("sigla") String sigla, Pageable pageable);

}
