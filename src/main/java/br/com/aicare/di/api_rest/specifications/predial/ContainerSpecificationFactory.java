package br.com.aicare.di.api_rest.specifications.predial;

import br.com.aicare.di.api_rest.dominio.predial.Container;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class ContainerSpecificationFactory {

    private ContainerSpecificationFactory() {
    }

    public static Specification<Container> busca(String busca) {
        return buscaNome(busca)
                .or(buscaDescricao(busca));
    }

    public static Specification<Container> buscaNome(String busca) {
        return (Root<Container> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Container> buscaDescricao(String busca) {
        return (Root<Container> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("descricao")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Container> compartimento(int idCompartimento) {
        return (Root<Container> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.equal(root.join("compartimento", JoinType.LEFT).get("id"), idCompartimento))
                .getRestriction();
    }

}
