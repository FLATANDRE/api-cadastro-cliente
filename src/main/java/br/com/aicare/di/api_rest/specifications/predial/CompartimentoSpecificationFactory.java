package br.com.aicare.di.api_rest.specifications.predial;

import br.com.aicare.di.api_rest.dominio.predial.Compartimento;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class CompartimentoSpecificationFactory {

    private CompartimentoSpecificationFactory() {
    }

    public static Specification<Compartimento> busca(String busca) {
        return buscaNome(busca)
                .or(buscaDescricao(busca));
    }

    public static Specification<Compartimento> buscaNome(String busca) {
        return (Root<Compartimento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }
    
     public static Specification<Compartimento> buscaDescricao(String busca) {
        return (Root<Compartimento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("descricao")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Compartimento> localizacaoFisica(int idLocalizacaoFisica) {
        return (Root<Compartimento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.equal(root.join("localizacaoFisica", JoinType.LEFT).get("id"), idLocalizacaoFisica))
                .getRestriction();
    }

}
