/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.nota_fiscal;

import br.com.aicare.di.api_rest.dominio.nota_fiscal.NotaFiscalCompra;
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
public interface NotaFiscalRepository extends PagingAndSortingRepository<NotaFiscalCompra, Integer>, JpaSpecificationExecutor<NotaFiscalCompra> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM NotaFiscalCompra t WHERE numero = :numero")
    public boolean numeroExistente(@Param("numero") String numero);

}
