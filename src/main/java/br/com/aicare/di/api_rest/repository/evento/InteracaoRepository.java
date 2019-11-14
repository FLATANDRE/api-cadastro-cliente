/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.evento;

import br.com.aicare.di.api_rest.dominio.evento.Interacao;
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
public interface InteracaoRepository extends PagingAndSortingRepository<Interacao, Integer>, JpaSpecificationExecutor<Interacao> {

    @Query("SELECT i FROM Interacao i WHERE fim IS NULL AND ((id_dispositivo1=:id_dispositivo1 AND id_dispositivo2=:id_dispositivo2) OR (id_dispositivo1=:id_dispositivo2 AND id_dispositivo2=:id_dispositivo1)) ORDER BY inicio DESC")
    public List<Interacao> obtemInteracaoCorrente(@Param("id_dispositivo1") int idDispositivo1, @Param("id_dispositivo2") int idDispositivo2);
    
}
