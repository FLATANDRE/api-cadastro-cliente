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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.NaturezaVinculo;
import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.Profissao;
import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.Vinculado;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaFisica;
import br.com.aicare.di.api_rest.dto.pessoal.VinculadoDTO;
import br.com.aicare.di.api_rest.repository.pessoal.NaturezaVinculoRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaFisicaRepository;
import br.com.aicare.di.api_rest.repository.pessoal.ProfissaoRepository;
import br.com.aicare.di.api_rest.repository.pessoal.VinculadoRepository;
import br.com.aicare.di.api_rest.specifications.pessoal.VinculadoSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;

/**
 *
 * @author Marcelo Preis
 */
@RestController
@RequestMapping("/pessoal/vinculado")
@Secured({"ROLE_ADA", "ROLE_ADM", "ROLE_ADH"})
@Api(tags = "Vínculado")
@CrossOrigin
public class VinculadoController {

    @Autowired
    private VinculadoRepository vinculadoRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private NaturezaVinculoRepository naturezaVinculoRepository;

    @Autowired
    private ProfissaoRepository profissaoRepository;

    @ApiOperation(value = "Lista os vinculados")
    @GetMapping
    public Page<Vinculado> listar(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "q", required = false) String q) {

        Specification<Vinculado> specification = Specification.where(null);

        if (q != null) {
            specification = specification.and(VinculadoSpecificationFactory.busca(q));
        }

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return vinculadoRepository.findAll(specification, pageable);
    }

    @ApiOperation(value = "Busca um vinculado pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Vinculado> buscar(@PathVariable Integer id) {
        Optional<Vinculado> vinculado = vinculadoRepository.findById(id);
        if (!vinculado.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }
        return new ResponseEntity<>(vinculado.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo vinculado")
    @PostMapping
    public ResponseEntity<Vinculado> criar(@RequestBody VinculadoDTO dto, UriComponentsBuilder ucBuilder) {
        Vinculado vinculado = new Vinculado();

        ResponseEntity<Vinculado> result = null;
        result = validarCampos(result, dto, vinculado, true);
        if (result != null) {
            return result;
        }
        vinculadoRepository.save(vinculado);

        return new ResponseEntity<>(vinculado, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Atualizar um vinculado")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Vinculado> atualizar(@PathVariable("id") int id, @RequestBody VinculadoDTO dto) {
        Optional<Vinculado> vinculadoAtual = vinculadoRepository.findById(id);

        Vinculado vinculado = vinculadoAtual.get();
        if (!vinculadoAtual.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }
        ResponseEntity<Vinculado> result = null;
        result = validarCampos(result, dto, vinculado, false);
        if (result != null) {
            return result;
        }

        vinculadoRepository.save(vinculado);
        return new ResponseEntity<>(vinculado, HttpStatus.CREATED);
    }

    private ResponseEntity<Vinculado> validarCampos(ResponseEntity<Vinculado> result, VinculadoDTO dto,
            Vinculado vinculado, boolean criarVinculado) {
        result = validarPessoaFisica(result, dto, vinculado);
        if (result != null) {
            return result;
        }
        result = validarMatricula(result, dto, vinculado, criarVinculado);
        if (result != null) {
            return result;
        }

        if (dto.getIdNaturezaVinculo() == null) {
            return ApiError.badRequest(Translator.toLocale("vinculado_natureza_vinculo_informe"));
        }
        Optional<NaturezaVinculo> naturezaVinculo = naturezaVinculoRepository.findById(dto.getIdNaturezaVinculo());
        if (!naturezaVinculo.isPresent()) {
            return ApiError.badRequest(Translator.toLocale("vinculado_natureza_vinculo_inesistente"));
        }
        vinculado.setNatureza(naturezaVinculo.get());

        if (dto.getIdProfissao() == null) {
            return ApiError.badRequest(Translator.toLocale("vinculado_profissao_informe"));
        }
        Optional<Profissao> profissao = profissaoRepository.findById(dto.getIdProfissao());
        if (!profissao.isPresent()) {
            return ApiError.badRequest(Translator.toLocale("vinculado_profissao_inesistente"));
        }
        vinculado.setProfissao(profissao.get());

        if (dto.getInicio() == null) {
            return ApiError.badRequest(Translator.toLocale("vinculado_data_inicio"));
        }
        vinculado.setInicio(dto.getInicio());

        result = validarObservacao(result, dto, vinculado);
        if (result != null) {
            return result;
        }

        vinculado.setFim(dto.getFim());

        return result;
    }

    private ResponseEntity<Vinculado> validarPessoaFisica(ResponseEntity<Vinculado> result, VinculadoDTO dto,
            Vinculado vinculado) {
        if (dto.getIdPessoaFisica() == null) {
            return ApiError.badRequest(Translator.toLocale("vinculado_pessoa_fisica_informe"));
        }
        Optional<PessoaFisica> pessoaFisica = pessoaFisicaRepository.findById(dto.getIdPessoaFisica());
        if (!pessoaFisica.isPresent()) {
            return ApiError.badRequest(Translator.toLocale("vinculado_pessoa_fisica_inesistente"));
        }
        vinculado.setPessoaFisica(pessoaFisica.get());
        return result;
    }

    private ResponseEntity<Vinculado> validarObservacao(ResponseEntity<Vinculado> result, VinculadoDTO dto,
            Vinculado vinculado) {
        if (dto.getObservacao() != null) {
            vinculado.setObservacao(dto.getObservacao());
        }
        return result;
    }

    private ResponseEntity<Vinculado> validarMatricula(ResponseEntity<Vinculado> result, VinculadoDTO dto,
            Vinculado vinculado, boolean criarVinculado) {
        if (dto.getMatricula() == null || dto.getMatricula().length() < 3) {
            return ApiError.badRequest(Translator.toLocale("vinculado_matricula"));
        }
        vinculado.setMatricula(dto.getMatricula());
        if (criarVinculado) {

            if (vinculadoRepository.matriculaExistente(dto.getMatricula())) {
                return ApiError.badRequest(Translator.toLocale("vinculado_matricula_duplicada"));
            }
        }

        return result;
    }

}
