/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.ordem_producao;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.dispositivo.ModeloDispositivo;
import br.com.aicare.di.api_rest.dominio.ordem_producao.OrdemProducao;
import br.com.aicare.di.api_rest.dominio.ordem_producao.OrdemProducaoModeloDispositivo;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import br.com.aicare.di.api_rest.dto.ordem_producao.ItemOrdemProducaoDTO;
import br.com.aicare.di.api_rest.dto.ordem_producao.OrdemProducaoDTO;
import br.com.aicare.di.api_rest.repository.dispositivo.ModeloDispositivoRepository;
import br.com.aicare.di.api_rest.repository.ordem_producao.OrdemProducaoRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaJuridicaRepository;
import br.com.aicare.di.api_rest.specifications.ordem_producao.OrdemProducaoSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/ordens-producao")
@Api(tags = "Ordens de produção", description = "Ordens de produção")
public class OrdemProducaoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrdemProducaoRepository ordemProducaoRepository;

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    ModeloDispositivoRepository modeloDispositivoRepository;

    @ApiOperation(value = "Lista ordens de produção")
    @GetMapping()
    public Page<OrdemProducao> listar(
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

        Specification<OrdemProducao> specification = Specification.where(null);

        if (q != null) {
            specification = specification.and(OrdemProducaoSpecificationFactory.busca(q));
        }

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return ordemProducaoRepository.findAll(specification, pageable);
    }

    @ApiOperation(value = "Busca uma ordem de produção pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrdemProducao> listar(@PathVariable Integer id) {
        Optional<OrdemProducao> objeto = ordemProducaoRepository.findById(id);

        if (!objeto.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(objeto.get(), HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "Cria uma ordem de produção")
    @PostMapping()
    public ResponseEntity<OrdemProducao> criar(@RequestBody OrdemProducaoDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            OrdemProducao objeto = new OrdemProducao();

            if (StringUtils.isBlank(dto.getNumero())) {
                return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_numero"));
            }
            if (ordemProducaoRepository.numeroExistente(dto.getNumero())) {
                return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_numero_existente"));
            }

            objeto.setNumero(dto.getNumero());

            if (dto.getData() == null || dto.getData().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_data"));
            }
            try {
                if (!dto.getData().matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_data"));
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date date = sdf.parse(dto.getData());
                objeto.setData(date);
            } catch (Exception e) {
                LOGGER.error("Erro ao realizar o convesão da data dto", e);
                return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_data"));
            }

            if (dto.getFornecedor() == null) {
                return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_fornecedor"));
            }
            Optional<PessoaJuridica> o = pessoaJuridicaRepository.findById(dto.getFornecedor());
            if (o.isPresent()) {
                objeto.setFornecedor(o.get());
            } else {
                return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_fornecedor_inesistente") + ": " + dto.getFornecedor());
            }

            if (dto.getItens() == null || dto.getItens().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_itens"));
            }

            Set<OrdemProducaoModeloDispositivo> itens = new HashSet<>();
            for (ItemOrdemProducaoDTO item : dto.getItens()) {
                if (item.getModeloDispositivo() == null) {
                    return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_item_modelo_dispositivo_invalido"));
                }
                Optional<ModeloDispositivo> modeloDispositivo = modeloDispositivoRepository.findById(item.getModeloDispositivo());
                if (!modeloDispositivo.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_item_modelo_dispositivo_invalido"));
                }
                if (modeloDispositivo.get().isDesconhecido()) {
                    return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_item_modelo_dispositivo_invalido"));
                }
                if (item.getQuantidade() == null || item.getQuantidade() < 1) {
                    return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_item_quantidade"));
                }
                if (item.getPrecoUnitario() == null || item.getPrecoUnitario() <= 0) {
                    return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_item_preco"));
                }
                OrdemProducaoModeloDispositivo ordemProducaoModeloDispositivo = new OrdemProducaoModeloDispositivo();
                ordemProducaoModeloDispositivo.setOrdemProducao(objeto);
                ordemProducaoModeloDispositivo.setModeloDispositivo(modeloDispositivo.get());
                ordemProducaoModeloDispositivo.setPrecoUnitario(item.getPrecoUnitario());
                ordemProducaoModeloDispositivo.setQuantidade(item.getQuantidade());

                itens.add(ordemProducaoModeloDispositivo);
            }
            if (itens.size() < 1) {
                return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_itens"));
            }
            objeto.setItens(itens);

            objeto.setDescricao(dto.getDescricao());

            OrdemProducao novo = ordemProducaoRepository.save(objeto);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar uma ordem de produção", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza uma ordem de produção")
    @PutMapping(value = "/{id}")
    public ResponseEntity<OrdemProducao> atualizar(@PathVariable("id") int id, @RequestBody OrdemProducaoDTO dto) {
        try {
            Optional<OrdemProducao> atual = ordemProducaoRepository.findById(id);

            if (!atual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            OrdemProducao objeto = atual.get();

            if (dto.getData() != null) {
                if (dto.getData().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_data"));
                }
                try {
                    if (!dto.getData().matches("\\d{4}-\\d{2}-\\d{2}")) {
                        return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_data"));
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    Date date = sdf.parse(dto.getData());
                    objeto.setData(date);
                } catch (Exception e) {
                    LOGGER.error("Erro ao realizar o convesão da data dto", e);
                    return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_data"));
                }
            }

            if (dto.getFornecedor() != null) {
                Optional<PessoaJuridica> o = pessoaJuridicaRepository.findById(dto.getFornecedor());
                if (o.isPresent()) {
                    objeto.setFornecedor(o.get());
                } else {
                    return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_fornecedor_inesistente") + ": " + dto.getFornecedor());
                }
            }

            if (dto.getDescricao() != null) {
                objeto.setDescricao(dto.getDescricao());
            }

            if (dto.getItens() != null) {
                Set<OrdemProducaoModeloDispositivo> itens = new HashSet<>();
                for (ItemOrdemProducaoDTO item : dto.getItens()) {
                    if (item.getModeloDispositivo() == null) {
                        return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_item_modelo_dispositivo_invalido"));
                    }
                    Optional<ModeloDispositivo> modeloDispositivo = modeloDispositivoRepository.findById(item.getModeloDispositivo());
                    if (!modeloDispositivo.isPresent()) {
                        return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_item_modelo_dispositivo_invalido"));
                    }
                    if (modeloDispositivo.get().isDesconhecido()) {
                        return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_item_modelo_dispositivo_invalido"));
                    }
                    if (item.getQuantidade() == null || item.getQuantidade() < 1) {
                        return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_item_quantidade"));
                    }
                    if (item.getPrecoUnitario() == null || item.getPrecoUnitario() <= 0) {
                        return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_item_preco"));
                    }
                    OrdemProducaoModeloDispositivo ordemProducaoModeloDispositivo = new OrdemProducaoModeloDispositivo();
                    ordemProducaoModeloDispositivo.setOrdemProducao(objeto);
                    ordemProducaoModeloDispositivo.setModeloDispositivo(modeloDispositivo.get());
                    ordemProducaoModeloDispositivo.setPrecoUnitario(item.getPrecoUnitario());
                    ordemProducaoModeloDispositivo.setQuantidade(item.getQuantidade());

                    itens.add(ordemProducaoModeloDispositivo);
                }
                if (itens.size() < 1) {
                    return ApiError.badRequest(Translator.toLocale("ordem_producao_validacao_itens"));
                }

                //Atualizo os itens existentes ou adiciono os novos
                for (OrdemProducaoModeloDispositivo item : itens) {
                    if (objeto.getItens().contains(item)) {
                        objeto.getItens().forEach(i -> {
                            if (i.equals(item)) {
                                i.setPrecoUnitario(item.getPrecoUnitario());
                                i.setQuantidade(item.getQuantidade());
                            }
                        });
                    } else {
                        objeto.getItens().add(item);
                    }
                }

                //Removo os itens que não estarão mais presentes
                List<OrdemProducaoModeloDispositivo> itensRemover = new ArrayList();
                Iterator<OrdemProducaoModeloDispositivo> it = objeto.getItens().iterator();
                while (it.hasNext()) {
                    OrdemProducaoModeloDispositivo item = it.next();
                    if (!itens.contains(item)) {
                        itensRemover.add(item);
                    }
                }
                objeto.getItens().removeAll(itensRemover);

            }

            //Atualizo o objeto utilizando o repositório
            OrdemProducao atualizado = ordemProducaoRepository.save(objeto);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar uma ordem de produção", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
