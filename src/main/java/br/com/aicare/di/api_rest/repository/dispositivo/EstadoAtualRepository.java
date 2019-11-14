/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.dispositivo;

import br.com.aicare.di.api_rest.dominio.dispositivo.EstadoAtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface EstadoAtualRepository extends JpaRepository<EstadoAtual, Integer> {

}
