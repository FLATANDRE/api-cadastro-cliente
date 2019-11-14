/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.fatura;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.aicare.di.api_rest.dominio.fatura.itens.insumo.ItemFaturaInsumo;
import br.com.aicare.di.api_rest.dominio.fatura.itens.tipo_insumo.ItemFaturaTipoInsumoPK;

@Repository
public interface ItemFaturaInsumoRepository extends PagingAndSortingRepository<ItemFaturaInsumo, ItemFaturaTipoInsumoPK>, JpaSpecificationExecutor<ItemFaturaInsumo> {

}
