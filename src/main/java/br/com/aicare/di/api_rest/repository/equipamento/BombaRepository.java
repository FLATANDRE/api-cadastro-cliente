/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.equipamento;

import br.com.aicare.di.api_rest.dominio.equipamento.bomba.Bomba;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fernanda
 */
@Repository
public interface BombaRepository extends PagingAndSortingRepository<Bomba, Integer>, JpaSpecificationExecutor<Bomba> {

    public Optional<Bomba> findBySerialNumber(String serialNumber);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Bomba t WHERE lower(serialNumber) = :serialNumber")
    public boolean serialNumberExistente(@Param("serialNumber") String serialNumber);

}
