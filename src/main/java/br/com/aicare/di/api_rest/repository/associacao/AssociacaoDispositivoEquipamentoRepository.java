/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.associacao;

import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivoEquipamento;
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
public interface AssociacaoDispositivoEquipamentoRepository extends PagingAndSortingRepository<AssociacaoDispositivoEquipamento, Integer>, JpaSpecificationExecutor<AssociacaoDispositivoEquipamento> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM AssociacaoDispositivoEquipamento t "
            + "WHERE id_dispositivo=:id_dispositivo "
            + "AND desassociacao IS NULL")
    public boolean isAssociacaoAtiva(@Param("id_dispositivo") int idDispositivo);

    @Query("SELECT i FROM AssociacaoDispositivoEquipamento i "
            + "WHERE id_dispositivo=:id_dispositivo "
            + "AND desassociacao IS NULL")
    public List<AssociacaoDispositivoEquipamento> obtemAssociacaoAtiva(@Param("id_dispositivo") int idDispositivo);
    
    @Query("SELECT i FROM AssociacaoDispositivoEquipamento i "
            + "WHERE id_equipamento=:id_equipamento "
            + "AND desassociacao IS NULL")
    public List<AssociacaoDispositivoEquipamento> obtemAssociacaoAtivaEquipamento(@Param("id_equipamento") int idDispositivo);

}
