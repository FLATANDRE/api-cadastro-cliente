/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.associacao;

import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivoContainer;
import java.util.List;
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
public interface AssociacaoDispositivoContainerRepository extends PagingAndSortingRepository<AssociacaoDispositivoContainer, Integer>, JpaSpecificationExecutor<AssociacaoDispositivoContainer> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM AssociacaoDispositivoContainer t "
            + "WHERE id_dispositivo=:id_dispositivo "
            + "AND desassociacao IS NULL")
    public boolean isAssociacaoAtiva(@Param("id_dispositivo") int idDispositivo);

    @Query("SELECT i FROM AssociacaoDispositivoContainer i "
            + "WHERE id_dispositivo=:id_dispositivo "
            + "AND desassociacao IS NULL")
    public List<AssociacaoDispositivoContainer> obtemAssociacaoAtiva(@Param("id_dispositivo") int idDispositivo);

}
