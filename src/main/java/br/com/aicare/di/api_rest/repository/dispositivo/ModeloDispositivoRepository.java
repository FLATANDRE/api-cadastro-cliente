/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.dispositivo;

import br.com.aicare.di.api_rest.dominio.dispositivo.ModeloDispositivo;
import br.com.aicare.di.api_rest.dto.dispositivo.ModeloDispositivoSimpleResponseDTO;

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
public interface ModeloDispositivoRepository extends PagingAndSortingRepository<ModeloDispositivo, Integer>, JpaSpecificationExecutor<ModeloDispositivo> {

    @Query("select md.id as id, md.nome as nome from ModeloDispositivo md")
    public Collection<ModeloDispositivoSimpleResponseDTO> all();
}
