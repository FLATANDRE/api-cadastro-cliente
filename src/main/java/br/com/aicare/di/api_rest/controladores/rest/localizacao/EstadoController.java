/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.localizacao;

import br.com.aicare.di.api_rest.dominio.localizacao.Estado;
import br.com.aicare.di.api_rest.repository.localizacao.EstadoRepository;
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
 * @author FERNANDA
 */
@RestController
@CrossOrigin
@RequestMapping("/localizacao/estado")
@Api(tags = "Estado", description = "Estado")
public class EstadoController {

    @Autowired
    EstadoRepository estadoRepository;

    @ApiOperation(value = "Lista os estados")
    @GetMapping()
    public Page<Estado> listar(
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

        Page<Estado> resultPage;

        if (q == null) {
            resultPage = estadoRepository.findAll(pageable);
        } else {
            resultPage = estadoRepository.busca(q.toLowerCase(), pageable);
        }

        return resultPage;
    }

    @ApiOperation(value = "Busca um estado pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Estado> buscar(@PathVariable Integer id) {
        Optional<Estado> estado = estadoRepository.findById(id);

        if (!estado.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(estado.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca todos os estados de um pais pelo sua sigla")
    @GetMapping(value = "/pais/{sigla}")
    public Page<Estado> buscar(
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
            @PathVariable String sigla) {

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();

        Page<Estado> resultPage;

        if (q == null) {
            resultPage = estadoRepository.todasPorPais(sigla.toLowerCase(), pageable);
        } else {
            resultPage = estadoRepository.buscaPorPais(q.toLowerCase(), sigla.toLowerCase(), pageable);
        }

        return resultPage;

    }

}
