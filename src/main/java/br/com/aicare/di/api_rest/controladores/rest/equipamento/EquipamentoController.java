/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.equipamento;

import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.equipamento.Equipamento;
import br.com.aicare.di.api_rest.dto.equipamento.EquipamentoSimpleResponseDTO;
import br.com.aicare.di.api_rest.repository.equipamento.EquipamentoRepository;
import br.com.aicare.di.api_rest.specifications.equipamento.EquipamentoSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_ADM"})
@RequestMapping("/equipamentos")
@Api(tags = "Equipamentos", description = "Equipamentos")
public class EquipamentoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @ApiOperation(value = "Lista os Equipamentos")
    @GetMapping()
    public Page<Equipamento> listar(
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
                    required = false) String sort,
            @ApiParam(required = false, name = "q", value = "Busca")
            @RequestParam(
                    value = "q",
                    required = false) String q
    ) {

        Specification<Equipamento> specification = Specification.where(null);

        if (q != null) {
            specification = specification.and(EquipamentoSpecificationFactory.busca(q));
        }

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return equipamentoRepository.findAll(specification, pageable);
    }

    @ApiOperation(value = "Lista os equipamentos com informações reduzidas")
    @GetMapping("/all")
    public Collection<EquipamentoSimpleResponseDTO> listar() {
        Collection<EquipamentoSimpleResponseDTO> all = equipamentoRepository.all();
        return all;
    }

    @ApiOperation(value = "Busca um Equipamento pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Equipamento> listar(@PathVariable Integer id) {
        Optional<Equipamento> equipamento = equipamentoRepository.findById(id);

        if (!equipamento.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(equipamento.get(), HttpStatus.OK);
    }

}
