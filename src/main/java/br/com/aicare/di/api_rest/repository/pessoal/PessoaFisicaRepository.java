/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.pessoal;

import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaFisica;
import br.com.aicare.di.api_rest.dto.pessoal.PessoaFisicaSimpleResponseDTO;
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
public interface PessoaFisicaRepository extends PagingAndSortingRepository<PessoaFisica, Integer> {

    @Query("select p.id as id, p.nome as nome, p.cpf as cpf from PessoaFisica p")
    public Collection<PessoaFisicaSimpleResponseDTO> all();

    public Page<PessoaFisica> findAll(Pageable pageable);

    @Query("SELECT pj FROM PessoaFisica pj "
            + "WHERE lower(nome) like %:busca% "
            + "or lower(cpf) like %:busca% "
            + "or lower(rg) like %:busca%")
    public Page<PessoaFisica> busca(@Param("busca") String busca, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM PessoaFisica t WHERE cpf = :cpf")
    public boolean cpfExistente(@Param("cpf") String cpf);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM PessoaFisica t WHERE rg = :rg")
    public boolean rgExistente(@Param("rg") String rg);

    public PessoaFisica findByCpf(String cpf);
}
