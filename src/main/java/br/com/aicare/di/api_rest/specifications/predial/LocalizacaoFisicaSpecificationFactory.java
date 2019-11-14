package br.com.aicare.di.api_rest.specifications.predial;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.aicare.di.api_rest.dominio.predial.LocalizacaoFisica;

public class LocalizacaoFisicaSpecificationFactory {

    private LocalizacaoFisicaSpecificationFactory() {
    }

    // @formatter:off
    public static Specification<LocalizacaoFisica> busca(String busca) {
        return buscaNome(busca)
                .or(buscaPessoaJuridicaNome(busca));
    }
    // @formatter:on

    public static Specification<LocalizacaoFisica> buscaNome(String busca) {
        return (Root<LocalizacaoFisica> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<LocalizacaoFisica> buscaLogradouro(String busca) {
        return (Root<LocalizacaoFisica> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) -> query.where(criteriaBuilder.like(
                criteriaBuilder.lower(root.join("endereco", JoinType.LEFT).get("logradouro")),
                "%" + busca.toLowerCase() + "%")).getRestriction();
    }

    public static Specification<LocalizacaoFisica> buscaCEP(String busca) {
        return (Root<LocalizacaoFisica> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("endereco", JoinType.LEFT).get("cep")),
                        "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<LocalizacaoFisica> buscaBairro(String busca) {
        return (Root<LocalizacaoFisica> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.join("endereco", JoinType.LEFT).get("bairro")),
                        "%" + busca.toLowerCase() + "%"))
                .getRestriction();
    }

    public static Specification<LocalizacaoFisica> buscaCidade(String busca) {
        return (Root<LocalizacaoFisica> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.like(criteriaBuilder.lower(root.get("endereco").get("cidade").get("nome")),
                        "%" + busca.toLowerCase() + "%".trim()))
                .getRestriction();
    }

    public static Specification<LocalizacaoFisica> buscaEstadoUF(String busca) {
        return (Root<LocalizacaoFisica> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) -> query.where(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("endereco").get("cidade").get("estado").get("uf")),
                "%" + busca.toLowerCase() + "%".trim())).getRestriction();
    }

    public static Specification<LocalizacaoFisica> buscaEstadoNome(String busca) {
        return (Root<LocalizacaoFisica> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) -> query.where(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("endereco").get("cidade").get("estado").get("nome")),
                "%" + busca.toLowerCase() + "%".trim())).getRestriction();
    }

    public static Specification<LocalizacaoFisica> buscaPessoaJuridicaNome(String busca) {
        return (Root<LocalizacaoFisica> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) -> query.where(criteriaBuilder.like(
                criteriaBuilder.lower(root.join("pessoaJuridica", JoinType.LEFT).get("nome")),
                "%" + busca.toLowerCase() + "%")).getRestriction();
    }

    public static Specification<LocalizacaoFisica> buscaPessoaJuridicaNomeFantasia(String busca) {
        return (Root<LocalizacaoFisica> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) -> query.where(criteriaBuilder.like(
                criteriaBuilder.lower(root.join("pessoaJuridica", JoinType.LEFT).get("nomeFantasia")),
                "%" + busca.toLowerCase() + "%")).getRestriction();
    }

    public static Specification<LocalizacaoFisica> buscaPessoaJuridicaCNPJ(String busca) {
        return (Root<LocalizacaoFisica> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) -> query.where(criteriaBuilder.like(
                criteriaBuilder.lower(root.join("pessoaJuridica", JoinType.LEFT).get("cnpj")),
                "%" + busca.toLowerCase() + "%")).getRestriction();
    }

    public static Specification<LocalizacaoFisica> pessoaJuridica(int idPessoaJuridica) {
        return (Root<LocalizacaoFisica> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> query
                .where(criteriaBuilder.equal(root.join("pessoaJuridica", JoinType.LEFT).get("id"), idPessoaJuridica))
                .getRestriction();
    }

}
