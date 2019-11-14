/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.evento;

import br.com.aicare.di.api_rest.dominio.evento.Evento;
import java.util.Date;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author pcollares
 */
public class EventoSpecificationFactory {

    public static Specification<Evento> busca(String busca) {
        return buscaMac(busca)
                .or(buscaTipo(busca));
    }

    public static Specification<Evento> buscaMac(String busca) {
        return (Root<Evento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("dispositivo", JoinType.LEFT).get("mac")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Evento> buscaTipo(String busca) {
        return (Root<Evento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("tipo")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<Evento> tipo(String tipo) {
        return (Root<Evento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.equal(root.get("tipo"), tipo.toUpperCase()))
                .getRestriction();
    }

    public static Specification<Evento> mac(String mac) {
        return (Root<Evento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.equal(criteriaBuilder.lower(root.join("dispositivo", JoinType.LEFT).get("mac")), mac.toLowerCase()))
                .getRestriction();
    }

    public static Specification<Evento> dataHoraMaiorOuIgualQue(Date dataHora) {
        return (Root<Evento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.<Date>get("dataHora"),
                                dataHora
                        )
                )
                .getRestriction();
    }

    public static Specification<Evento> dataHoraMenorOuIgualQue(Date dataHora) {
        return (Root<Evento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.<Date>get("dataHora"),
                                dataHora
                        )
                )
                .getRestriction();
    }

    public static Specification<Evento> dataHoraEntre(Date dataHoraInicial, Date dataHoraFinal) {
        return (Root<Evento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(
                        criteriaBuilder.between(
                                root.<Date>get("dataHora"),
                                dataHoraInicial,
                                dataHoraFinal
                        )
                )
                .getRestriction();
    }

}
