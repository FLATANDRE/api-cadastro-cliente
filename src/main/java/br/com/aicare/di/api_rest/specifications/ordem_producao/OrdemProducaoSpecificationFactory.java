/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.ordem_producao;

import br.com.aicare.di.api_rest.dominio.ordem_producao.OrdemProducao;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author pcollares
 */
public class OrdemProducaoSpecificationFactory {

    public static Specification<OrdemProducao> busca(String busca) {
        return buscaNumero(busca)
                .or(buscaFornecedor(busca));
    }

    public static Specification<OrdemProducao> buscaNumero(String busca) {
        return (Root<OrdemProducao> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("numero")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<OrdemProducao> buscaFornecedor(String busca) {
        return (Root<OrdemProducao> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("fornecedor", JoinType.LEFT).get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }
}
