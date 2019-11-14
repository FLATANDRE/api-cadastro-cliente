/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.pessoal;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.NaturezaVinculo;
import br.com.aicare.di.api_rest.repository.pessoal.NaturezaVinculoRepository;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author FERNANDA
 */
@RestController
@CrossOrigin
@RequestMapping("/pessoal/natureza-vinculo")
@Api(tags = "Natureza Vínculo")
public class NaturezaVinculoController {

    @Autowired
    NaturezaVinculoRepository naturezaVinculoRepository;

    @ApiOperation(value = "Lista as naturezas")
    @GetMapping()
    public Page<?> listar(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
	    @RequestParam(value = "size", required = false, defaultValue = "10") int size,
	    @RequestParam(value = "sort", required = false) String sort,
	    @RequestParam(value = "q", required = false) String q) {

	Pageable pageable = new PageableFactory(page, size, sort).getPageable();
	final Page<NaturezaVinculo> resultPage;

	if (q == null) {
	    resultPage = naturezaVinculoRepository.findAll(pageable);
	} else {
	    resultPage = naturezaVinculoRepository.busca(q.toLowerCase(), pageable);
	}

	return resultPage;

    }

    @ApiOperation(value = "Busca uma natureza vinculo pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<NaturezaVinculo> buscar(@PathVariable Integer id) {
	Optional<NaturezaVinculo> naturezaVinculo = naturezaVinculoRepository.findById(id);

	if (!naturezaVinculo.isPresent()) {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	return new ResponseEntity<>(naturezaVinculo.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria uma nova natureza vinculo")
    @PostMapping()
    public ResponseEntity<NaturezaVinculo> criar(@RequestBody NaturezaVinculo naturezaVinculo,
	    UriComponentsBuilder ucBuilder) {

	naturezaVinculoRepository.save(naturezaVinculo);

	return new ResponseEntity<>(naturezaVinculo, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualiza uma natureza vinculo")
    @PutMapping(value = "/{id}")
    public ResponseEntity<NaturezaVinculo> atualizar(@PathVariable("id") int id,
	    @RequestBody NaturezaVinculo naturezaVinculo) {
	Optional<NaturezaVinculo> vinculoAtual = naturezaVinculoRepository.findById(id);

	if (!vinculoAtual.isPresent()) {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	if (naturezaVinculo.getNome() != null) {
	    vinculoAtual.get().setNome(naturezaVinculo.getNome());
	}

	naturezaVinculoRepository.saveAndFlush(vinculoAtual.get());

	return new ResponseEntity<>(vinculoAtual.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Remove uma natureza vinculo")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
	Optional<NaturezaVinculo> naturezaVinculo = naturezaVinculoRepository.findById(id);

	if (naturezaVinculo == null) {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	} else {
	    naturezaVinculoRepository.deleteById(id);
	}

	return new ResponseEntity<>(HttpStatus.OK);
    }

}
