/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.autenticacao;

import br.com.aicare.di.api_rest.dominio.autenticacao.Perfil;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    public Optional<Perfil> findByCodigo(String codigo);
}
