/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.localizacao;

import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.localizacao.Pais;
import br.com.aicare.di.api_rest.repository.localizacao.EstadoRepository;
import br.com.aicare.di.api_rest.repository.localizacao.PaisRepository;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Paulo
 */
@RestController
@CrossOrigin
@RequestMapping("/localizacao/pais")
@Api(tags = "paises", description = "paises")
public class PaisController {

    @Autowired
    PaisRepository paisRepository;

    @Autowired
    EstadoRepository estadoRepository;

    @ApiOperation(value = "Lista os paises")
    @GetMapping()
    public Page<Pais> listar(
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
                    required = false) String q
    ) {
        Pageable pageable = new PageableFactory(page, size, sort).getPageable();

        Page<Pais> resultPage;

        if (q == null) {
            resultPage = paisRepository.findAll(pageable);
        } else {
            resultPage = paisRepository.busca(q.toLowerCase(), pageable);
        }

        return resultPage;
    }

    @ApiOperation(value = "Busca um pais pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity buscar(@PathVariable Integer id) {
        Optional<Pais> cidade = paisRepository.findById(id);

        if (!cidade.isPresent()) {
            return ApiError.badRequest(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(cidade.get(), HttpStatus.OK);
    }

}
