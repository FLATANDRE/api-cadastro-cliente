/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.dispositivo;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.dispositivo.FabricanteDispositivo;
import br.com.aicare.di.api_rest.dominio.dispositivo.ModeloDispositivo;
import br.com.aicare.di.api_rest.dto.dispositivo.ModeloDispositivoDTO;
import br.com.aicare.di.api_rest.dto.dispositivo.ModeloDispositivoSimpleResponseDTO;
import br.com.aicare.di.api_rest.repository.dispositivo.FabricanteDispositivoRepository;
import br.com.aicare.di.api_rest.repository.dispositivo.ModeloDispositivoRepository;
import br.com.aicare.di.api_rest.specifications.dispositivo.ModeloDispositivoSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Collection;
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
@RequestMapping("/dispositivos/modelos")
@Api(tags = "Modelos Dispositivos", description = "Modelos de Dispositivos")
public class ModeloDispositivoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ModeloDispositivoRepository modeloRepository;

    @Autowired
    FabricanteDispositivoRepository fabricanteRepository;

    @ApiOperation(value = "Lista os modelos de Dispositivos")
    @GetMapping()
    public Page<ModeloDispositivo> listar(
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

        Specification<ModeloDispositivo> specification = Specification.where(null);

        if (q != null) {
            specification = specification.and(ModeloDispositivoSpecificationFactory.busca(q));
        }

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return modeloRepository.findAll(specification, pageable);
    }

    @ApiOperation(value = "Lista os modelos  de dispositivos com informações reduzidas")
    @GetMapping("/all")
    public Collection<ModeloDispositivoSimpleResponseDTO> listar() {
        Collection<ModeloDispositivoSimpleResponseDTO> all = modeloRepository.all();
        return all;
    }

    @ApiOperation(value = "Busca um modelo pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ModeloDispositivo> listar(@PathVariable Integer id) {
        Optional<ModeloDispositivo> modelo = modeloRepository.findById(id);

        if (!modelo.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(modelo.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo modelo")
    @PostMapping()
    public ResponseEntity<ModeloDispositivo> criar(@RequestBody ModeloDispositivoDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            ModeloDispositivo modelo = new ModeloDispositivo();

            if (dto.getNome() == null || dto.getNome().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_nome"));
            }
            modelo.setNome(dto.getNome());

            if (dto.getFabricante() == null) {
                dto.setFabricante(1);
            }
            Optional<FabricanteDispositivo> fabricante = fabricanteRepository.findById(dto.getFabricante());
            if (!fabricante.isPresent()) {
                return ApiError.badRequest(Translator.toLocale("validacao_fabricante"));
            }
            modelo.setFabricante(fabricante.get());

            if (dto.getTensaoMaximaBateria() != null) {
                modelo.setTensaoMaximaBateria(dto.getTensaoMaximaBateria());
            }

            if (dto.getTensaoMinimaBateria() != null) {
                modelo.setTensaoMinimaBateria(dto.getTensaoMinimaBateria());
            }

            if (dto.getIntervaloManutencaoObrigatoria() != null) {
                modelo.setIntervaloManutencaoObrigatoria(dto.getIntervaloManutencaoObrigatoria());
            }

            ModeloDispositivo novo = modeloRepository.save(modelo);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar um dispositivos", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza um modelo")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ModeloDispositivo> atualizar(@PathVariable("id") int id, @RequestBody ModeloDispositivoDTO dto) {
        try {
            Optional<ModeloDispositivo> atual = modeloRepository.findById(id);

            if (!atual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            if (dto.getNome() != null) {
                if (dto.getNome().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_nome"));
                }
                atual.get().setNome(dto.getNome());
            }

            if (dto.getFabricante() != null) {
                Optional<FabricanteDispositivo> fabricante = fabricanteRepository.findById(dto.getFabricante());
                if (!fabricante.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_fabricante"));
                }
                atual.get().setFabricante(fabricante.get());
            }

            if (dto.getTensaoMaximaBateria() != null) {
                atual.get().setTensaoMaximaBateria(dto.getTensaoMaximaBateria());
            }

            if (dto.getTensaoMinimaBateria() != null) {
                atual.get().setTensaoMinimaBateria(dto.getTensaoMinimaBateria());
            }

            if (dto.getIntervaloManutencaoObrigatoria() != null) {
                atual.get().setIntervaloManutencaoObrigatoria(dto.getIntervaloManutencaoObrigatoria());
            }

            //Atualizo o objeto utilizando o repositório
            ModeloDispositivo atualizado = modeloRepository.save(atual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar um dispositivo", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
