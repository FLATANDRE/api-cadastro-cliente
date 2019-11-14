/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.localizacao;

import br.com.aicare.di.api_rest.dominio.localizacao.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
    
}
