/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.evento;

import br.com.aicare.di.api_rest.dominio.evento.Evento;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface EventoRepository extends PagingAndSortingRepository<Evento, Integer>, JpaSpecificationExecutor<Evento> {

    @Modifying
    @Transactional // Make sure to import org.springframework.transaction.annotation.Transactional
    public void deleteByDataHoraBefore(Date date);

}
