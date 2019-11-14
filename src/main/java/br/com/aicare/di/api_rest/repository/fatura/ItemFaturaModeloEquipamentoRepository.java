/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.fatura;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.aicare.di.api_rest.dominio.fatura.itens.modelo_equipamento.ItemFaturaModeloEquipamento;
import br.com.aicare.di.api_rest.dominio.fatura.itens.modelo_equipamento.ItemFaturaModeloEquipamentoPK;

@Repository
public interface ItemFaturaModeloEquipamentoRepository extends PagingAndSortingRepository<ItemFaturaModeloEquipamento, ItemFaturaModeloEquipamentoPK>, JpaSpecificationExecutor<ItemFaturaModeloEquipamento> {

}
