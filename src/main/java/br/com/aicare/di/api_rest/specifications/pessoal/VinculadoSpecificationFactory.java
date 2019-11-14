/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.specifications.pessoal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.Vinculado;

/**
 *
 * @author Paulo Collares
 */
public class VinculadoSpecificationFactory {

    // @formatter:off
    public static Specification<Vinculado> busca(String busca) {
	return buscaNome(busca)
		.or(matriculaLike(busca))
		.or(buscaRG(busca))
		.or(buscaCPF(busca));
    }
    // @formatter:on

    public static Specification<Vinculado> buscaNome(String busca) {
	return (Root<Vinculado> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
		.where(criteriaBuilder.like(criteriaBuilder.lower(root.join("pessoaFisica", JoinType.LEFT).get("nome")),
			"%" + busca.toLowerCase() + "%"))
		.getRestriction();
    }

    public static Specification<Vinculado> buscaRG(String busca) {
	return (Root<Vinculado> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
		.where(criteriaBuilder.like(criteriaBuilder.lower(root.join("pessoaFisica", JoinType.LEFT).get("rg")),
			"%" + busca.toLowerCase() + "%"))
		.getRestriction();
    }

    public static Specification<Vinculado> buscaCPF(String busca) {
	return (Root<Vinculado> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
		.where(criteriaBuilder.like(criteriaBuilder.lower(root.join("pessoaFisica", JoinType.LEFT).get("cpf")),
			"%" + busca.toLowerCase() + "%"))
		.getRestriction();
    }

    public static Specification<Vinculado> matriculaLike(String busca) {
	return (Root<Vinculado> root, CriteriaQuery<?> query,
		CriteriaBuilder criteriaBuilder) -> query.where(criteriaBuilder
			.like(criteriaBuilder.lower(root.get("matricula")), "%" + busca.toLowerCase() + "%"))
			.getRestriction();
    }

    public static Specification<Vinculado> matricula(String matricula) {
	return (Root<Vinculado> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
		.where(criteriaBuilder.equal(root.get("matricula"), matricula)).getRestriction();
    }

}
