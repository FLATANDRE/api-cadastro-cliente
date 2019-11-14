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

import br.com.aicare.di.api_rest.dominio.contrato.alocacao.AlocacaoContratoDispositivo;

@Repository
public interface AlocacaoContratoDispositivoRepository extends PagingAndSortingRepository<AlocacaoContratoDispositivo, Integer>, JpaSpecificationExecutor<AlocacaoContratoDispositivo> {

    @Query("select acd from AlocacaoContratoDispositivo acd WHERE contrato.id = :idContrato")
    public Collection<AlocacaoContratoDispositivo> dispositivosAlocados(@Param("idContrato") Integer idContrato);

    @Query("select acd from AlocacaoContratoDispositivo acd")
    public Collection<AlocacaoContratoDispositivo> all();
}