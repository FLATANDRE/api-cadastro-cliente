/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.autenticacao;

import br.com.aicare.di.api_rest.dominio.autenticacao.Usuario;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaFisica;
import java.util.Optional;
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
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Integer> {

    public Optional<Usuario> findByLogin(String login);

    public Optional<Usuario> findByPessoa(PessoaFisica pessoaFisica);

    public Page<Usuario> findAll(Pageable pageable);

    @Query("SELECT t FROM Usuario t "
            + "WHERE lower(login) like %:busca% ")
    public Page<Usuario> busca(@Param("busca") String busca, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Usuario t WHERE lower(login) = :login")
    public boolean loginExistente(@Param("login") String login);

    @Query("SELECT CASE WHEN t.habilitado=true THEN true ELSE false END FROM Usuario t WHERE login = :login")
    public boolean isHabilitado(@Param("login") String login);

}
