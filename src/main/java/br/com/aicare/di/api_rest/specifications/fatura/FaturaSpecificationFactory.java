/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.fatura;

import org.springframework.data.jpa.domain.Specification;

import br.com.aicare.di.api_rest.dominio.fatura.Fatura;


public class FaturaSpecificationFactory {
    
    public static Specification<Fatura> busca(String busca) {
        return buscaNumeroNotaFiscal(busca);
    }

    public static Specification<Fatura> buscaNumeroNotaFiscal(String busca) {
        return (root, query, cb) -> cb.equal(root.get("numeroNotaFiscal"), busca);
    }

    
}
