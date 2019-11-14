/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.equipamento;

import br.com.aicare.di.api_rest.dominio.equipamento.bomba.TipoBomba;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoBombaRepository extends PagingAndSortingRepository<TipoBomba, Integer> {

    public Optional<TipoBomba> findById(Integer id);

}