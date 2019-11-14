/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.equipamento;

import br.com.aicare.di.api_rest.dominio.equipamento.bomba.RackBomba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fernanda
 */
@Repository
public interface RackBombaRepository extends JpaRepository<RackBomba, Integer> {
    
}
