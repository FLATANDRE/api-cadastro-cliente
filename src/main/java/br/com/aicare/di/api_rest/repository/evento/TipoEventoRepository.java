/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.evento;

import br.com.aicare.di.api_rest.dominio.evento.TipoEvento;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface TipoEventoRepository extends JpaRepository<TipoEvento, Integer> {

    public Optional<TipoEvento> findByCodigo(String codigo);

}
