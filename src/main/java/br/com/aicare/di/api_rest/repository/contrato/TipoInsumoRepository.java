/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.contrato;

import br.com.aicare.di.api_rest.dominio.insumo.TipoInsumo;
import br.com.aicare.di.api_rest.dto.contrato.TipoInsumoSimpleResponseDTO;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoInsumoRepository extends PagingAndSortingRepository<TipoInsumo, Integer> {

    public Optional<TipoInsumo> findById(Integer id);

    @Query("select ti.id as id, ti.nome as nome from TipoInsumo ti")
    public Collection<TipoInsumoSimpleResponseDTO> all();

}
