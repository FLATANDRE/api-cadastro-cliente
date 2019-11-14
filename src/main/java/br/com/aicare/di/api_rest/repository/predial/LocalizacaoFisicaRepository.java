package br.com.aicare.di.api_rest.repository.predial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import br.com.aicare.di.api_rest.dominio.localizacao.Endereco;
import br.com.aicare.di.api_rest.dominio.predial.LocalizacaoFisica;
import br.com.aicare.di.api_rest.dto.predial.LocalizacaoFisicaSimpleResponseDTO;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Marcelo Preis
 */
public interface LocalizacaoFisicaRepository extends PagingAndSortingRepository<LocalizacaoFisica, Integer>, JpaSpecificationExecutor<LocalizacaoFisica> {

    @Query("select p.id as id, p.nome as nome from LocalizacaoFisica p where pessoa_juridica=:pj")
    public Collection<LocalizacaoFisicaSimpleResponseDTO> all(@Param("pj") int pj);

    public Page<LocalizacaoFisica> findAll(Pageable pageable);

    @Query("SELECT pf FROM LocalizacaoFisica pf WHERE lower(nome) like %:busca% ")
    public Page<LocalizacaoFisica> busca(@Param("busca") String busca, Pageable pageable);

    public LocalizacaoFisica findByEndereco(Endereco endereco);

    public List<LocalizacaoFisica> findByPessoaJuridicaId(int id);
}
