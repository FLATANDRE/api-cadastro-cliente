/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.manutencao;

import br.com.aicare.di.api_rest.dominio.equipamento.Manutencao;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author pcollares
 */
public class ManutencaoSpecificationFactory {

    public static Specification<Manutencao> busca(String busca) {
        return buscaSerialNumber(busca)
                .or(buscaNomeResponsavel(busca));
    }

    public static Specification<Manutencao> buscaSerialNumber(String busca) {
        return (Root<Manutencao> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("equipamento", JoinType.LEFT).get("serialNumber")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Manutencao> buscaNomeResponsavel(String busca) {
        return (Root<Manutencao> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("responsavel", JoinType.LEFT).get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Manutencao> equipamento(int id) {
        return (root, query, cb) -> cb.equal(root.join("equipamento", JoinType.LEFT).get("id"), id);
    }
}
