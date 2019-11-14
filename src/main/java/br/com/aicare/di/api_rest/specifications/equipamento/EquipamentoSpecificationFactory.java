/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.equipamento;

import br.com.aicare.di.api_rest.dominio.equipamento.Equipamento;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author pcollares
 */
public class EquipamentoSpecificationFactory {

    public static Specification<Equipamento> operativo(boolean operativo) {
        return (root, query, cb) -> cb.equal(root.get("operativo"), operativo);
    }

    public static Specification<Equipamento> busca(String busca) {
        return buscaSerialnumber(busca)
                .or(buscaModelo(busca))
                .or(buscaFabricante(busca));
    }

    public static Specification<Equipamento> buscaSerialnumber(String busca) {
        return (Root<Equipamento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("serialNumber")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Equipamento> buscaModelo(String busca) {
        return (Root<Equipamento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("modelo", JoinType.LEFT).get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Equipamento> buscaFabricante(String busca) {
        return (Root<Equipamento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("modelo", JoinType.LEFT).join("fabricante", JoinType.LEFT).get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }
}
