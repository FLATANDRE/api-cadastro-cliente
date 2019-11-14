/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.equipamento;

import br.com.aicare.di.api_rest.dominio.equipamento.Equipamento;
import br.com.aicare.di.api_rest.dominio.equipamento.ModeloEquipamento;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ModeloEquipamentoSpecificationFactory {

    public static Specification<ModeloEquipamento> busca(String busca) {
        return buscaNome(busca);
    }

    public static Specification<ModeloEquipamento> buscaNome(String busca) {
        return (Root<ModeloEquipamento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    
}
