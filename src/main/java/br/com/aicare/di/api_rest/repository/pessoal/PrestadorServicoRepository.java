/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.pessoal;

import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.PrestadorServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author FERNANDA
 */
@Repository
public interface PrestadorServicoRepository extends JpaRepository<PrestadorServico, Integer> {

}
