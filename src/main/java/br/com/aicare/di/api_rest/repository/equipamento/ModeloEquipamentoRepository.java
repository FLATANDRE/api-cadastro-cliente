/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.equipamento;

import br.com.aicare.di.api_rest.dominio.equipamento.ModeloEquipamento;
import br.com.aicare.di.api_rest.dto.equipamento.ModeloEquipamentoSimpleResponseDTO;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface ModeloEquipamentoRepository extends PagingAndSortingRepository<ModeloEquipamento, Integer>, JpaSpecificationExecutor<ModeloEquipamento> {

    @Query("select me.id as id, me.nome as nome from ModeloEquipamento me")
    public Collection<ModeloEquipamentoSimpleResponseDTO> all();
}
