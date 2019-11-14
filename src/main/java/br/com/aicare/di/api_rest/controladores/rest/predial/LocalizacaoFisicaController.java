package br.com.aicare.di.api_rest.controladores.rest.predial;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import br.com.aicare.di.api_rest.dominio.localizacao.Cidade;
import br.com.aicare.di.api_rest.dominio.localizacao.Endereco;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import br.com.aicare.di.api_rest.dominio.predial.LocalizacaoFisica;
import br.com.aicare.di.api_rest.dto.predial.LocalizacaoFisicaDTO;
import br.com.aicare.di.api_rest.dto.predial.LocalizacaoFisicaSimpleResponseDTO;
import br.com.aicare.di.api_rest.repository.localizacao.CidadeRepository;
import br.com.aicare.di.api_rest.repository.localizacao.EnderecoRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaJuridicaRepository;
import br.com.aicare.di.api_rest.repository.predial.LocalizacaoFisicaRepository;
import br.com.aicare.di.api_rest.specifications.predial.LocalizacaoFisicaSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.List;
import org.springframework.security.access.annotation.Secured;

/**
 *
 * @author Marcelo Preis
 */
@RestController
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_ADM", "ROLE_ADH"})
@RequestMapping("/predial/localizacao-fisica")
@Api(tags = {"Localizaca Física"})
public class LocalizacaoFisicaController {

    private final Logger LOGGER = LoggerFactory.getLogger(LocalizacaoFisicaController.class);

    @Autowired
    private LocalizacaoFisicaRepository localizacaoFisicaRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public LocalizacaoFisicaController() {
        LOGGER.info("create controller...");
    }

    @ApiOperation(value = "Listar as Localização Física Nome ou Endereço")
    @GetMapping
    public Page<LocalizacaoFisica> listar(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "pj", required = true) int pj) {

        Specification<LocalizacaoFisica> specificationQ = Specification.where(null);

        if (q != null) {
            specificationQ = specificationQ.and(LocalizacaoFisicaSpecificationFactory.busca(q));
        }
        specificationQ = specificationQ.and(LocalizacaoFisicaSpecificationFactory.pessoaJuridica(pj));

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return localizacaoFisicaRepository.findAll(specificationQ, pageable);

    }
    
    @ApiOperation(value = "Lista as localizações fisicas com informações reduzidas")
    @GetMapping("/all")
    public Collection<LocalizacaoFisicaSimpleResponseDTO> listar( @RequestParam(value = "pj", required = true) int pj) {
        Collection<LocalizacaoFisicaSimpleResponseDTO> all = localizacaoFisicaRepository.all(pj);
        return all;
    }

    @ApiOperation(value = "Busca uma Localização Física pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<LocalizacaoFisica> buscar(@PathVariable Integer id) {
        LocalizacaoFisica localizacaoFisica = localizacaoFisicaRepository.findById(id).get();

        if (localizacaoFisica == null) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(localizacaoFisica, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista as Localizações Físicas por ID de pessoa jurídica")
    @GetMapping(value = "/pj/{id}")
    public ResponseEntity<List<LocalizacaoFisica>> listarLocalizacaoPorPJ(@PathVariable int id) {

        LOGGER.info("Pesquisando lista de localização física por PJ. ID = " + id);

        List<LocalizacaoFisica> localizacoes = localizacaoFisicaRepository.findByPessoaJuridicaId(id);

        if (localizacoes.size() <= 0) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(localizacoes, HttpStatus.OK);
    }

    @ApiOperation(value = "Cria uma nova Localização Física")
    @PostMapping()
    public ResponseEntity<LocalizacaoFisica> criar(@RequestBody LocalizacaoFisicaDTO dto,
            UriComponentsBuilder ucBuilder) {

        final LocalizacaoFisica localizacaoFisica = new LocalizacaoFisica();

        ResponseEntity<LocalizacaoFisica> result = null;
        result = validarCampos(result, dto, localizacaoFisica, false);
        if (result != null) {
            return result;
        }
        localizacaoFisicaRepository.save(localizacaoFisica);

        return new ResponseEntity<>(localizacaoFisica, HttpStatus.CREATED);

    }

    private ResponseEntity<LocalizacaoFisica> validarCampos(ResponseEntity<LocalizacaoFisica> result,
            LocalizacaoFisicaDTO dto, LocalizacaoFisica localizacaoFisica, boolean atualizar) {

        localizacaoFisica.setNome(dto.getNome());

        if (dto.getIdPessoaJuridica() <= 0) {
            return ApiError.badRequest(Translator.toLocale("localizacao_fisica_pessoa_juridica_informe"));
        }
        Optional<PessoaJuridica> pessoaJuridica = pessoaJuridicaRepository.findById(dto.getIdPessoaJuridica());
        if (!pessoaJuridica.isPresent()) {
            return ApiError.badRequest(Translator.toLocale("localizacao_fisica_pessoa_juridica_inesistente"));
        }
        localizacaoFisica.setPessoaJuridica(pessoaJuridica.get());

        // endereco
        if (dto.getIdEndereco() < 0) {
            return ApiError.badRequest(Translator.toLocale("localizacao_fisica_endereco_informe"));
        }
        Optional<Endereco> endereco = enderecoRepository.findById(dto.getIdEndereco());
        if (atualizar && !endereco.isPresent()) {
            return ApiError.badRequest(Translator.toLocale("localizacao_fisica_endereco_inesistente"));
        }
        if (!atualizar && !endereco.isPresent()) {
            localizacaoFisica.setEndereco(new Endereco());
        } else {
            localizacaoFisica.setEndereco(endereco.get());
        }

        // cep
        if (StringUtils.isBlank(dto.getCep())) {
            return ApiError.badRequest(Translator.toLocale("localizacao_fisica_cep_informe"));
        }
        localizacaoFisica.getEndereco().setCep(dto.getCep());

        // logradouro
        if (StringUtils.isBlank(dto.getLogradouro())) {
            return ApiError.badRequest(Translator.toLocale("localizacao_fisica_logradouro_informe"));
        }
        localizacaoFisica.getEndereco().setLogradouro(dto.getLogradouro());

        // bairro
        if (StringUtils.isBlank(dto.getBairro())) {
            return ApiError.badRequest(Translator.toLocale("localizacao_fisica_bairro_informe"));
        }
        localizacaoFisica.getEndereco().setBairro(dto.getBairro());

        // cidade
        if (dto.getIdCidade() <= 0) {
            return ApiError.badRequest(Translator.toLocale("localizacao_fisica_cidade_informe"));
        }
        Optional<Cidade> cidade = cidadeRepository.findById(dto.getIdCidade());
        if (!cidade.isPresent()) {
            return ApiError.badRequest(Translator.toLocale("localizacao_fisica_cidade_inesistente"));
        }
        localizacaoFisica.getEndereco().setCidade(cidade.get());

        enderecoRepository.save(localizacaoFisica.getEndereco());
        return null;
    }

    @ApiOperation(value = "Atualiza uma Localização Física")
    @PutMapping(value = "/{id}")
    public ResponseEntity<LocalizacaoFisica> atualizar(@PathVariable("id") int id,
            @RequestBody LocalizacaoFisicaDTO dto) {
        Optional<LocalizacaoFisica> localizacaoFisicaOptional = localizacaoFisicaRepository.findById(id);
        LocalizacaoFisica localizacaoFisicaAtual = localizacaoFisicaOptional.get();
        if (!localizacaoFisicaOptional.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }
        ResponseEntity<LocalizacaoFisica> result = null;
        result = validarCampos(result, dto, localizacaoFisicaAtual, false);
        if (result != null) {
            return result;
        }
        localizacaoFisicaRepository.save(localizacaoFisicaAtual);
        return new ResponseEntity<>(localizacaoFisicaAtual, HttpStatus.CREATED);

    }

}
