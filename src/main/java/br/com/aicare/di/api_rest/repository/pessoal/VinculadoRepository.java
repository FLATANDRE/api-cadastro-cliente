/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.repository.pessoal;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.Vinculado;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Paulo Collares
 */
@Repository
public interface VinculadoRepository extends PagingAndSortingRepository<Vinculado, Integer>, JpaSpecificationExecutor<Vinculado> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Vinculado t WHERE lower(matricula) = :matricula")
    public boolean matriculaExistente(@Param("matricula") String matricula);

    public Vinculado findByMatricula(String matricula);
    
}
