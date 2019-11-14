/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.equipamento;

import br.com.aicare.di.api_rest.dominio.equipamento.bomba.Bomba;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class BombaSpecificationFactory {

    public static Specification<Bomba> busca(String busca) {
        return buscaSerialNumber(busca)
                .or(buscaModelo(busca))
                .or(buscaFabricante(busca));
    }

    public static Specification<Bomba> buscaSerialNumber(String busca) {
        return (root, query, cb) -> cb.like(root.get("serialNumber"), "%" + busca.toLowerCase() + "%");
    }

    public static Specification<Bomba> buscaModelo(String busca) {
        return (Root<Bomba> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("modelo", JoinType.LEFT).get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Bomba> buscaFabricante(String busca) {
        return (Root<Bomba> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("modelo", JoinType.LEFT).join("fabricante", JoinType.LEFT).get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

}
