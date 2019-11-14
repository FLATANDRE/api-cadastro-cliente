/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.suporte;

import br.com.aicare.di.api_rest.dominio.configuracao.Configuracao;
import br.com.aicare.di.api_rest.dto.configuracao.ConfiguracaoPublicDTO;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface ConfiguracaoRepository extends PagingAndSortingRepository<Configuracao, Integer>, JpaSpecificationExecutor<Configuracao> {

    @Query("select c from Configuracao c where id=:id")
    public Collection<ConfiguracaoPublicDTO> getPublic(@Param("id") int id);

}
