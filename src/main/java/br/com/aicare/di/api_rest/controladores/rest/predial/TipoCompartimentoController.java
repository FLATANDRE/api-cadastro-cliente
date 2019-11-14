/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.predial;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.predial.TipoCompartimento;
import br.com.aicare.di.api_rest.dto.predial.TipoCompartimentoDTO;
import br.com.aicare.di.api_rest.repository.predial.TipoCompartimentoRepository;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@RestController
@Secured({"ROLE_ADA"})
@CrossOrigin
@RequestMapping("/predial/compartimentos/tipos")
@Api(tags = "Tipos de compartimentos", description = "Tipos de compartimentos")
public class TipoCompartimentoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TipoCompartimentoRepository tipoCompartimentosRepository;

    @ApiOperation(value = "Lista os tipos de compartimentos")
    @GetMapping()
    public Page<TipoCompartimento> listar(
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
        return tipoCompartimentosRepository.findAll(pageable);
    }

    @ApiOperation(value = "Busca um tipo Compartimento pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TipoCompartimento> listar(@PathVariable Integer id) {
        Optional<TipoCompartimento> tipoCompartimento = tipoCompartimentosRepository.findById(id);

        if (!tipoCompartimento.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(tipoCompartimento.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo tipo Compartimento")
    @PostMapping()
    public ResponseEntity<TipoCompartimento> criar(@RequestBody TipoCompartimentoDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            TipoCompartimento tipoCompartimento = new TipoCompartimento();

            if (dto.getNome() == null || dto.getNome().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_nome"));
            }
            tipoCompartimento.setNome(dto.getNome());

            TipoCompartimento novo = tipoCompartimentosRepository.save(tipoCompartimento);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar um tipo Compartimento", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza um Tipo de Compartimento")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TipoCompartimento> atualizar(@PathVariable("id") int id, @RequestBody TipoCompartimentoDTO dto) {
        try {
            Optional<TipoCompartimento> atual = tipoCompartimentosRepository.findById(id);

            if (!atual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            if (dto.getNome() != null) {
                if (dto.getNome().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_nome"));
                }
                atual.get().setNome(dto.getNome());
            }

            //Atualizo o objeto utilizando o repositório
            TipoCompartimento atualizado = tipoCompartimentosRepository.save(atual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar um tipo Compartimento", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
