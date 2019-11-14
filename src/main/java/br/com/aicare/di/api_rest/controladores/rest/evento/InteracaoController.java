/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.evento;

import br.com.aicare.di.api_rest.dominio.evento.Interacao;
import br.com.aicare.di.api_rest.repository.evento.InteracaoRepository;
import br.com.aicare.di.api_rest.specifications.evento.InteracaoSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@Secured({"ROLE_ADA"})
@RequestMapping("/eventos/interacoes")
@Api(tags = "Eventos de interações", description = "Eventos de interações")
public class InteracaoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    InteracaoRepository interacaoRepository;

    @ApiOperation(value = "Lista todos os eventos de interacao")
    @GetMapping
    public Page<Interacao> listar(
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size,
            @RequestParam(
                    value = "sort",
                    required = false) String sort,
            @RequestParam(
                    value = "q",
                    required = false) String q,
            @ApiParam(required = false, name = "mac", value = "Filtra por MAC")
            @RequestParam(
                    value = "mac",
                    required = false) String mac,
            @ApiParam(required = false, name = "c", value = "Correntes ou não [1|0]")
            @RequestParam(
                    value = "c",
                    required = false) String c
    ) {

        Specification<Interacao> specification = Specification.where(null);

        if (mac != null) {
            specification = specification.and(InteracaoSpecificationFactory.mac(mac));
        }

        if (c != null && c.equals("1")) {
            specification = specification.and(InteracaoSpecificationFactory.correntes());
        }

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();

        Page<Interacao> resultPage = interacaoRepository.findAll(specification, pageable);
        return resultPage;
    }

}
