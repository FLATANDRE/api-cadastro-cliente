/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.equipamento;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.equipamento.FabricanteEquipamento;
import br.com.aicare.di.api_rest.dto.equipamento.FabricanteEquipamentoDTO;
import br.com.aicare.di.api_rest.repository.equipamento.FabricanteEquipamentoRepository;
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
@CrossOrigin
@Secured({"ROLE_ADA"})
@RequestMapping("/equipamentos/fabricantes")
@Api(tags = "Fabricantes Equipamento", description = "Fabricantes de Equipamento")
public class FabricanteEquipamentoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FabricanteEquipamentoRepository fabricanteRepository;

    @ApiOperation(value = "Lista os fabricantes de equipamento")
    @GetMapping()
    public Page<FabricanteEquipamento> listar(
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
        return fabricanteRepository.findAll(pageable);
    }

    @ApiOperation(value = "Busca um fabricante de equipamento pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<FabricanteEquipamento> listar(@PathVariable Integer id) {
        Optional<FabricanteEquipamento> fabricante = fabricanteRepository.findById(id);

        if (!fabricante.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(fabricante.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo fabricante de equipamento")
    @PostMapping()
    public ResponseEntity<FabricanteEquipamento> criar(@RequestBody FabricanteEquipamentoDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            FabricanteEquipamento fabricante = new FabricanteEquipamento();

            if (dto.getNome() == null || dto.getNome().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_nome"));
            }
            fabricante.setNome(dto.getNome());

            FabricanteEquipamento novo = fabricanteRepository.save(fabricante);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar um fabricante de equipamento", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza um fabricante de equipamento")
    @PutMapping(value = "/{id}")
    public ResponseEntity<FabricanteEquipamento> atualizar(@PathVariable("id") int id, @RequestBody FabricanteEquipamentoDTO dto) {
        try {
            Optional<FabricanteEquipamento> atual = fabricanteRepository.findById(id);

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
            FabricanteEquipamento atualizado = fabricanteRepository.save(atual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar um fabricante de equipamento", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
