/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.dispositivo;

import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author pcollares
 */
public class DispositivoSpecificationFactory {

    public static Specification<Dispositivo> operativo(boolean operativo) {
        return (root, query, cb) -> cb.equal(root.get("operativo"), operativo);
    }

    public static Specification<Dispositivo> busca(String busca) {
        return buscaMac(busca)
                .or(buscaNamespace(busca))
                .or(buscaModelo(busca))
                .or(buscaFabricante(busca));
    }

    public static Specification<Dispositivo> buscaMac(String busca) {
        return (Root<Dispositivo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("mac")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Dispositivo> buscaNamespace(String busca) {
        return (Root<Dispositivo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("nameSpace")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Dispositivo> tipo(String tipo) {
        return (root, query, cb) -> cb.equal(root.join("tipoDispositivo", JoinType.LEFT).get("codigo"), tipo);
    }

    public static Specification<Dispositivo> buscaModelo(String busca) {
        return (Root<Dispositivo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("modelo", JoinType.LEFT).get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Dispositivo> buscaFabricante(String busca) {
        return (Root<Dispositivo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("modelo", JoinType.LEFT).join("fabricante", JoinType.LEFT).get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }
}
