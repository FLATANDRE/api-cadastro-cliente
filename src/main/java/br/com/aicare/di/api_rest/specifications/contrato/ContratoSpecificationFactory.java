/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.contrato;

import br.com.aicare.di.api_rest.dominio.contrato.Contrato;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class ContratoSpecificationFactory {
    
    public static Specification<Contrato> busca(String busca) {
        return buscaNumeroContrato(busca)
                .or(buscaNomeContratado(busca))
                .or(buscaNomeContratante(busca))
                .or(buscaCnpjContratante(busca));
    }

    public static Specification<Contrato> buscaNumeroContrato(String busca) {
        return (root, query, cb) -> cb.equal(root.get("numero"), busca);
    }

    public static Specification<Contrato> buscaNomeContratado(String busca) {
        return (Root<Contrato> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("contratado", JoinType.LEFT).get("nomeFantasia")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }
    
    public static Specification<Contrato> buscaNomeContratante(String busca) {
        return (Root<Contrato> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("contratante", JoinType.LEFT).get("nomeFantasia")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }    

    public static Specification<Contrato> buscaCnpjContratante(String busca) {
        return (Root<Contrato> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("contratante", JoinType.LEFT).get("cnpj")), busca))
                .getRestriction();
    }  
    
}
