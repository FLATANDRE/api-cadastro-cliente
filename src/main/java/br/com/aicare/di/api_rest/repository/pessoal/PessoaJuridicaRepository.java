/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.pessoal;

import br.com.aicare.di.api_rest.dto.pessoal.PessoaJuridicaSimpleResponseDTO;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author FERNANDA
 */
@Repository
public interface PessoaJuridicaRepository extends PagingAndSortingRepository<PessoaJuridica, Integer> {

    @Query("select p.id as id, p.nome as nome, p.cnpj as cnpj from PessoaJuridica p")
    public Collection<PessoaJuridicaSimpleResponseDTO> all();

    public Page<PessoaJuridica> findAll(Pageable pageable);

    @Query("SELECT pj FROM PessoaJuridica pj "
            + "WHERE lower(nome) like %:busca% "
            + "or lower(nomeFantasia) like %:busca% "
            + "or cnpj like %:busca% "
            + "or inscricaoEstadual like %:busca%")
    public Page<PessoaJuridica> busca(@Param("busca") String busca, Pageable pageable);

    public PessoaJuridica findByCnpj(String cnpj);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM PessoaJuridica t WHERE cnpj = :cnpj")
    public boolean cnpjExistente(@Param("cnpj") String cnpj);
}
