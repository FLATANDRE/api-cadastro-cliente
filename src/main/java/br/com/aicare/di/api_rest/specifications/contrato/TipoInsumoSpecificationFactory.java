/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.contrato;

import br.com.aicare.di.api_rest.dominio.insumo.TipoInsumo;
import org.springframework.data.jpa.domain.Specification;


public class TipoInsumoSpecificationFactory {
    
    public static Specification<TipoInsumo> busca(String busca) {
        return buscaNome(busca)
                .or(buscaCodigo(busca));
    }

    public static Specification<TipoInsumo> buscaNome(String busca) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("nome")), "%" + busca.toLowerCase() + "%");
    }

    public static Specification<TipoInsumo> buscaCodigo(String busca) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("codigo")), "%" + busca.toLowerCase() + "%");
    }

      
    
}
