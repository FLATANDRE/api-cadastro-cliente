/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.contrato;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.aicare.di.api_rest.dominio.contrato.alocacao.AlocacaoContratoEquipamento;

@Repository
public interface AlocacaoContratoEquipamentoRepository extends PagingAndSortingRepository<AlocacaoContratoEquipamento, Integer>, JpaSpecificationExecutor<AlocacaoContratoEquipamento> {

    @Query("select acd from AlocacaoContratoEquipamento acd WHERE contrato.id = :idContrato")
    public Collection<AlocacaoContratoEquipamento> equipamentosAlocados(@Param("idContrato") Integer idContrato);

    @Query("select acd from AlocacaoContratoEquipamento acd")
    public Collection<AlocacaoContratoEquipamento> all();
}