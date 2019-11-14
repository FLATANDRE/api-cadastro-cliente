/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.equipamento;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.equipamento.bomba.TipoBomba;
import br.com.aicare.di.api_rest.dto.equipamento.bomba_infusao.TipoBombaDTO;
import br.com.aicare.di.api_rest.repository.equipamento.TipoBombaRepository;
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

/**
 *
 * @author Paulo Collares
 */
@RestController
@Secured({"ROLE_ADA"})
@CrossOrigin
@RequestMapping("/equipamentos/bombas/tipos")
@Api(tags = "Tipo Bomba de infusão", description = "Tipo de Bomba de infusão")
public class TipoBombaController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TipoBombaRepository tipoBombaRepository;

    @ApiOperation(value = "Lista os tipos de bomba de infusão")
    @GetMapping()
    public Page<TipoBomba> listar(
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
        return tipoBombaRepository.findAll(pageable);
    }

    @ApiOperation(value = "Busca um tipo de bomba pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TipoBomba> listar(@PathVariable Integer id) {
        Optional<TipoBomba> tipoBomba = tipoBombaRepository.findById(id);

        if (!tipoBomba.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(tipoBomba.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo tipo bomba")
    @PostMapping()
    public ResponseEntity<TipoBomba> criar(@RequestBody TipoBombaDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            TipoBomba tipoBomba = new TipoBomba();

            if (dto.getNome() == null || dto.getNome().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_nome"));
            }
            tipoBomba.setNome(dto.getNome());

            TipoBomba novo = tipoBombaRepository.save(tipoBomba);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar um tipo de bomba", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza um tipo bomba")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TipoBomba> atualizar(@PathVariable("id") int id, @RequestBody TipoBombaDTO dto) {
        try {
            Optional<TipoBomba> atual = tipoBombaRepository.findById(id);

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
            TipoBomba atualizado = tipoBombaRepository.save(atual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar um tipo de bomba", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
