/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.manutencao;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.equipamento.TipoManutencao;
import br.com.aicare.di.api_rest.repository.manutencao.TipoManutencaoRepository;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Paulo Collares
 */
@RestController
@Secured({"ROLE_ADA", "ROLE_ADM", "ROLE_GMN", "ROLE_GCM"})
@CrossOrigin
@RequestMapping("/equipamentos/manutencoes/tipos")
@Api(tags = "Tipo manutenção", description = "Tipo de manutenção")
public class TipoManutencaoController {

    @Autowired
    TipoManutencaoRepository tipoManutencaoRepository;

    @ApiOperation(value = "Lista os tipos de manutenção")
    @GetMapping()
    public Page<TipoManutencao> listar(
            @ApiParam(required = false, name = "page", value = "Número da página")
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @ApiParam(required = false, name = "size", value = "Tamanho da página")
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size,
            @ApiParam(required = false, name = "sort", value = "Ordenação [coluna],[asc|desc]")
            @RequestParam(
                    value = "sort",
                    required = false) String sort
    ) {

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return tipoManutencaoRepository.findAll(pageable);
    }

    @ApiOperation(value = "Busca um tipo de manutenção")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TipoManutencao> listar(@PathVariable Integer id) {
        Optional<TipoManutencao> tipo = tipoManutencaoRepository.findById(id);

        if (!tipo.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(tipo.get(), HttpStatus.OK);
    }

}
