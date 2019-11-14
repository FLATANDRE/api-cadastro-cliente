/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.autenticacao;

import br.com.aicare.di.api_rest.dominio.autenticacao.ApiKey;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author Paulo Collares
 */
public class ApiKeySpecificationFactory {

    public static Specification<ApiKey> busca(String busca) {
        return buscaNome(busca);
    }

    public static Specification<ApiKey> buscaNome(String busca) {
        return (Root<ApiKey> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }
}
