/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.contrato;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuais;

@Repository
public interface CondicoesContratuaisRepository extends PagingAndSortingRepository<CondicoesContratuais, Integer>, JpaSpecificationExecutor<CondicoesContratuais> {

    
}