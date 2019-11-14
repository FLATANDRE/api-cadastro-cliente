/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.dispositivo;

import br.com.aicare.di.api_rest.dominio.dispositivo.TipoDispositivo;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface TipoDispositivoRepository extends PagingAndSortingRepository<TipoDispositivo, Integer> {

    public Optional<TipoDispositivo> findByCodigo(String codigo);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM TipoDispositivo t WHERE codigo = :codigo")
    public boolean codigoExistente(@Param("codigo") String codigo);

}
