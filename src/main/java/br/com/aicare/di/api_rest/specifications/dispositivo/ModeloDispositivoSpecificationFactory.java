/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.dispositivo;

import br.com.aicare.di.api_rest.dominio.dispositivo.ModeloDispositivo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ModeloDispositivoSpecificationFactory {

    public static Specification<ModeloDispositivo> busca(String busca) {
        return buscaNome(busca);
    }

    public static Specification<ModeloDispositivo> buscaNome(String busca) {
        return (Root<ModeloDispositivo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    
}
