/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.dispositivo;

import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface DispositivoRepository extends PagingAndSortingRepository<Dispositivo, Integer>, JpaSpecificationExecutor<Dispositivo> {

    public Optional<Dispositivo> findByMac(String mac);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Dispositivo t WHERE lower(mac) = :mac")
    public boolean macExistente(@Param("mac") String mac);

}
