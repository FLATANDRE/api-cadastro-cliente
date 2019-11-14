/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.equipamento;

import br.com.aicare.di.api_rest.dominio.equipamento.Equipamento;
import br.com.aicare.di.api_rest.dto.equipamento.EquipamentoSimpleResponseDTO;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface EquipamentoRepository extends PagingAndSortingRepository<Equipamento, Integer>, JpaSpecificationExecutor<Equipamento> {

    public Optional<Equipamento> findBySerialNumber(String serialNumber);

    @Query("select p.id as id, p.serialNumber as serialNumber from Equipamento p")
    public Collection<EquipamentoSimpleResponseDTO> all();

}
