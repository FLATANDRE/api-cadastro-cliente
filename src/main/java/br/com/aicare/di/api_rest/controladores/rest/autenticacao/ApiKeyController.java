/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.autenticacao;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.configuracoes.seguranca.TokenProvider;
import br.com.aicare.di.api_rest.dominio.autenticacao.ApiKey;
import br.com.aicare.di.api_rest.dto.autenticacao.ApiKeyDTO;
import br.com.aicare.di.api_rest.repository.autenticacao.ApikeyRepository;
import br.com.aicare.di.api_rest.specifications.autenticacao.ApiKeySpecificationFactory;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@Secured({"ROLE_ADA"})
@RequestMapping("/autenticacao/apikey")
@Api(tags = "API keys", description = "API keys")
public class ApiKeyController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    ApikeyRepository apikeyRepository;

    @ApiOperation(value = "Lista as api keys")
    @GetMapping()
    public Page<ApiKey> listar(
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

        Specification<ApiKey> specification = Specification.where(null);

        if (q != null) {
            specification = specification.and(ApiKeySpecificationFactory.busca(q));
        }

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return apikeyRepository.findAll(specification, pageable);
    }

    @ApiOperation(value = "Busca uma api key pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiKey> listar(@PathVariable int id) {
        Optional<ApiKey> api = apikeyRepository.findById(id);

        if (!api.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(api.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria uma nova apikey")
    @PostMapping()
    public ResponseEntity<ApiKey> criar(@RequestBody ApiKeyDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            ApiKey apiKey = new ApiKey();

            if (dto.isHabilitado() == null) {
                apiKey.setHabilitado(true);
            } else {
                apiKey.setHabilitado(dto.isHabilitado());
            }

            if (dto.getNome() == null || dto.getNome().isEmpty()) {
                return ApiError.badRequest("Informe o nome");
            }
            apiKey.setNome(dto.getNome());

            try {
                if (!dto.getDataVencimento().matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return ApiError.badRequest("Data invalida");
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date date = sdf.parse(dto.getDataVencimento());
                if (date.toInstant().isBefore(Instant.now())) {
                    return ApiError.badRequest("Data invalida");
                }
                apiKey.setDataVencimento(date);
            } catch (Exception e) {
                LOGGER.error("Erro ao realizar o convesão da data dto", e);
                return ApiError.badRequest("Data invalida");
            }

            apiKey.setDataCriacao(new Date());

            apiKey.setKey(gerarNovaApiKey(apiKey));

            //Salvo o objeto utilizando o repositório
            ApiKey novo = apikeyRepository.save(apiKey);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar uma apikey", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza uma apikey")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiKey> atualizar(@PathVariable("id") int id, @RequestBody ApiKeyDTO dto) {
        try {
            Optional<ApiKey> apiKeyAtual = apikeyRepository.findById(id);

            if (!apiKeyAtual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            if (dto.isHabilitado() != null) {
                apiKeyAtual.get().setHabilitado(dto.isHabilitado());
            }

            if (dto.getNome() != null) {
                if (dto.getNome().isEmpty()) {
                    return ApiError.badRequest("Informe o nome");
                }
                apiKeyAtual.get().setNome(dto.getNome());
            }

            if (dto.getDataVencimento() != null) {
                try {
                    if (!dto.getDataVencimento().matches("\\d{4}-\\d{2}-\\d{2}")) {
                        return ApiError.badRequest("Data invalida");
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    Date date = sdf.parse(dto.getDataVencimento());
                    if (date.toInstant().isBefore(Instant.now())) {
                        return ApiError.badRequest("Data invalida");
                    }
                    apiKeyAtual.get().setDataVencimento(date);
                } catch (Exception e) {
                    LOGGER.error("Erro ao realizar o convesão da data dto", e);
                    return ApiError.badRequest("Data invalida");
                }
            }

            if (dto.getGerarNovaKey() != null) {
                if (dto.getGerarNovaKey()) {
                    apiKeyAtual.get().setDataCriacao(new Date());
                    apiKeyAtual.get().setKey(gerarNovaApiKey(apiKeyAtual.get()));
                }
            }

            //Atualizo o objeto utilizando o repositório
            ApiKey atualizado = apikeyRepository.save(apiKeyAtual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar uma apikey", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

    private String gerarNovaApiKey(ApiKey apiKey) {
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        SecureRandom secureRandom = new SecureRandom();

        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);

        StringBuilder sb
                = new StringBuilder("aicare.apikey")
                        .append(".")
                        .append(apiKey.getNome().toLowerCase().replaceAll(" ", "_"))
                        .append(".")
                        .append(apiKey.getDataCriacao().getTime())
                        .append(".")
                        .append(base64Encoder.encodeToString(randomBytes));

        return base64Encoder.encodeToString(sb.toString().getBytes());
    }

}
