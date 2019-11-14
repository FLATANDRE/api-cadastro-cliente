/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.pessoal;

import br.com.aicare.di.api_rest.dominio.pessoal.GrupoPJ;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author pcollares
 */
public class GrupoPJSpecificationFactory {

    public static Specification<GrupoPJ> busca(String busca) {
        return buscaNome(busca)
                .or(buscaCnpjParticipante(busca))
                .or(buscaNomeParticipante(busca))
                .or(buscaNomeFantasiaParticipante(busca));
    }

    public static Specification<GrupoPJ> buscaNome(String busca) {
        return (Root<GrupoPJ> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("nomeFantasia")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<GrupoPJ> buscaCnpjParticipante(String busca) {
        return (Root<GrupoPJ> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("participantes", JoinType.LEFT).get("cnpj")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<GrupoPJ> buscaNomeParticipante(String busca) {
        return (Root<GrupoPJ> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("participantes", JoinType.LEFT).get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<GrupoPJ> buscaNomeFantasiaParticipante(String busca) {
        return (Root<GrupoPJ> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("participantes", JoinType.LEFT).get("nomeFantasia")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

}
