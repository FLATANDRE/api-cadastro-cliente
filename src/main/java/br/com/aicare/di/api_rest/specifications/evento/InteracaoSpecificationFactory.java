/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.evento;

import br.com.aicare.di.api_rest.dominio.evento.Interacao;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author pcollares
 */
public class InteracaoSpecificationFactory {

    public static Specification<Interacao> mac(String mac) {
        return macDispositivo1(mac)
                .or(macDispositivo2(mac));
    }

    public static Specification<Interacao> macDispositivo1(String mac) {
        return (Root<Interacao> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.equal(criteriaBuilder.lower(root.join("dispositivo1", JoinType.LEFT).get("mac")), mac.toLowerCase()))
                .getRestriction();
    }

    public static Specification<Interacao> macDispositivo2(String mac) {
        return (Root<Interacao> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.equal(criteriaBuilder.lower(root.join("dispositivo2", JoinType.LEFT).get("mac")), mac.toLowerCase()))
                .getRestriction();
    }

    public static Specification<Interacao> correntes() {
        return (Root<Interacao> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.isNull(root.get("fim")))
                .getRestriction();
    }

}
