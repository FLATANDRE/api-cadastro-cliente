/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.pessoal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.Profissao;
import br.com.aicare.di.api_rest.repository.pessoal.ProfissaoRepository;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

/**
 *
 * @author FERNANDA
 */
@RestController
@CrossOrigin
@RequestMapping("/pessoal/profissao")
@Api(tags = "Profissão")
public class ProfissaoController {

    @Autowired
    ProfissaoRepository profissaoRepository;

    @GetMapping()
    public Page<Profissao> listar(
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
            @RequestParam(
                    value = "q",
                    required = false) String q
    ) {

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();

        Page<Profissao> resultPage;

        if (q == null) {
            resultPage = profissaoRepository.findAll(pageable);
        } else {
            resultPage = profissaoRepository.busca(q.toLowerCase(), pageable);
        }

        return resultPage;
    }


}
