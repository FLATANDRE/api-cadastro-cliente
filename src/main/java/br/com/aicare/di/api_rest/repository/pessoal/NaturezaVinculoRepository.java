/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.pessoal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.NaturezaVinculo;

/**
 *
 * @author FERNANDA
 */
@Repository
public interface NaturezaVinculoRepository extends JpaRepository<NaturezaVinculo, Integer> {

    @Query("SELECT v FROM NaturezaVinculo v  WHERE lower(v.nome) like %:busca% ")
    public Page<NaturezaVinculo> busca(@Param("busca") String busca, Pageable pageable);
}
