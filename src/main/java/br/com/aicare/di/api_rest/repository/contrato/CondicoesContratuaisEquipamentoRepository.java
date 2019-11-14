/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.contrato;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuaisEquipamento;
import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuaisEquipamentoPK;

@Repository
public interface CondicoesContratuaisEquipamentoRepository extends PagingAndSortingRepository<CondicoesContratuaisEquipamento, CondicoesContratuaisEquipamentoPK>, JpaSpecificationExecutor<CondicoesContratuaisEquipamento> {

    
}