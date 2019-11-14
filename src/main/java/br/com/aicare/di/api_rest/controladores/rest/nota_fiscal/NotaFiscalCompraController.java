/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.nota_fiscal;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.dispositivo.ModeloDispositivo;
import br.com.aicare.di.api_rest.dominio.nota_fiscal.NotaFiscalCompra;
import br.com.aicare.di.api_rest.dominio.nota_fiscal.NotaFiscalCompraModeloDispositivo;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import br.com.aicare.di.api_rest.dto.nota_fiscal.ItemNotaFiscalCompraDTO;
import br.com.aicare.di.api_rest.dto.nota_fiscal.NotaFiscalCompraDTO;
import br.com.aicare.di.api_rest.repository.dispositivo.ModeloDispositivoRepository;
import br.com.aicare.di.api_rest.repository.nota_fiscal.NotaFiscalRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaJuridicaRepository;
import br.com.aicare.di.api_rest.specifications.nota_fiscal.NotaFiscalCompraSpecificationFactory;
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
@RequestMapping("/notas-fiscais-compra")
@Api(tags = "Notas fiscais de compra", description = "Notas fiscais de compra")
public class NotaFiscalCompraController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NotaFiscalRepository notaFiscalRepository;

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    ModeloDispositivoRepository modeloDispositivoRepository;

    @ApiOperation(value = "Lista notas fiscais de compra")
    @GetMapping()
    public Page<NotaFiscalCompra> listar(
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

        Specification<NotaFiscalCompra> specification = Specification.where(null);

        if (q != null) {
            specification = specification.and(NotaFiscalCompraSpecificationFactory.busca(q));
        }

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return notaFiscalRepository.findAll(specification, pageable);
    }

    @ApiOperation(value = "Busca uma nota fiscal de compra pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<NotaFiscalCompra> listar(@PathVariable Integer id) {
        Optional<NotaFiscalCompra> objeto = notaFiscalRepository.findById(id);

        if (!objeto.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(objeto.get(), HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "Cria uma nota fiscal de compra")
    @PostMapping()
    public ResponseEntity<NotaFiscalCompra> criar(@RequestBody NotaFiscalCompraDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            NotaFiscalCompra objeto = new NotaFiscalCompra();

            if (StringUtils.isBlank(dto.getNumero())) {
                return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_numero"));
            }
            if (notaFiscalRepository.numeroExistente(dto.getNumero())) {
                return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_numero_existente"));
            }

            objeto.setNumero(dto.getNumero());

            if (dto.getData() == null || dto.getData().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_data"));
            }
            try {
                if (!dto.getData().matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_data"));
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date date = sdf.parse(dto.getData());
                objeto.setData(date);
            } catch (Exception e) {
                LOGGER.error("Erro ao realizar o convesão da data dto", e);
                return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_data"));
            }

            if (dto.getFornecedor() == null) {
                return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_fornecedor"));
            }
            Optional<PessoaJuridica> o = pessoaJuridicaRepository.findById(dto.getFornecedor());
            if (o.isPresent()) {
                objeto.setFornecedor(o.get());
            } else {
                return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_fornecedor_inesistente") + ": " + dto.getFornecedor());
            }

            if (dto.getItens() == null || dto.getItens().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_itens"));
            }

            Set<NotaFiscalCompraModeloDispositivo> itens = new HashSet<>();
            for (ItemNotaFiscalCompraDTO item : dto.getItens()) {
                if (item.getModeloDispositivo() == null) {
                    return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_item_modelo_dispositivo_invalido"));
                }
                Optional<ModeloDispositivo> modeloDispositivo = modeloDispositivoRepository.findById(item.getModeloDispositivo());
                if (!modeloDispositivo.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_item_modelo_dispositivo_invalido"));
                }
                if (modeloDispositivo.get().isDesconhecido()) {
                    return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_item_modelo_dispositivo_invalido"));
                }
                if (item.getQuantidade() == null || item.getQuantidade() < 1) {
                    return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_item_quantidade"));
                }
                if (item.getPrecoUnitario() == null || item.getPrecoUnitario() <= 0) {
                    return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_item_preco"));
                }
                NotaFiscalCompraModeloDispositivo notaFiscalCompraModeloDispositivo = new NotaFiscalCompraModeloDispositivo();
                notaFiscalCompraModeloDispositivo.setNotaFiscal(objeto);
                notaFiscalCompraModeloDispositivo.setModeloDispositivo(modeloDispositivo.get());
                notaFiscalCompraModeloDispositivo.setPrecoUnitario(item.getPrecoUnitario());
                notaFiscalCompraModeloDispositivo.setQuantidade(item.getQuantidade());

                itens.add(notaFiscalCompraModeloDispositivo);
            }
            if (itens.size() < 1) {
                return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_itens"));
            }
            objeto.setItens(itens);

            objeto.setDescricao(dto.getDescricao());

            NotaFiscalCompra novo = notaFiscalRepository.save(objeto);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar uma nota fiscal", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @Transactional
    @ApiOperation(value = "Atualiza uma nota fiscal de compra")
    @PutMapping(value = "/{id}")
    public ResponseEntity<NotaFiscalCompra> atualizar(@PathVariable("id") int id, @RequestBody NotaFiscalCompraDTO dto) {
        try {
            Optional<NotaFiscalCompra> atual = notaFiscalRepository.findById(id);

            if (!atual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            NotaFiscalCompra objeto = atual.get();

            if (dto.getData() != null) {
                if (dto.getData().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_data"));
                }
                try {
                    if (!dto.getData().matches("\\d{4}-\\d{2}-\\d{2}")) {
                        return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_data"));
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    Date date = sdf.parse(dto.getData());
                    objeto.setData(date);
                } catch (Exception e) {
                    LOGGER.error("Erro ao realizar o convesão da data dto", e);
                    return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_data"));
                }
            }

            if (dto.getFornecedor() != null) {
                Optional<PessoaJuridica> o = pessoaJuridicaRepository.findById(dto.getFornecedor());
                if (o.isPresent()) {
                    objeto.setFornecedor(o.get());
                } else {
                    return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_fornecedor_inesistente") + ": " + dto.getFornecedor());
                }
            }

            if (dto.getDescricao() != null) {
                objeto.setDescricao(dto.getDescricao());
            }

            if (dto.getItens() != null) {
                Set<NotaFiscalCompraModeloDispositivo> itens = new HashSet<>();
                for (ItemNotaFiscalCompraDTO item : dto.getItens()) {
                    if (item.getModeloDispositivo() == null) {
                        return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_item_modelo_dispositivo_invalido"));
                    }
                    Optional<ModeloDispositivo> modeloDispositivo = modeloDispositivoRepository.findById(item.getModeloDispositivo());
                    if (!modeloDispositivo.isPresent()) {
                        return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_item_modelo_dispositivo_invalido"));
                    }
                    if (modeloDispositivo.get().isDesconhecido()) {
                        return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_item_modelo_dispositivo_invalido"));
                    }
                    if (item.getQuantidade() == null || item.getQuantidade() < 1) {
                        return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_item_quantidade"));
                    }
                    if (item.getPrecoUnitario() == null || item.getPrecoUnitario() <= 0) {
                        return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_item_preco"));
                    }
                    NotaFiscalCompraModeloDispositivo notaFiscalCompraModeloDispositivo = new NotaFiscalCompraModeloDispositivo();
                    notaFiscalCompraModeloDispositivo.setNotaFiscal(objeto);
                    notaFiscalCompraModeloDispositivo.setModeloDispositivo(modeloDispositivo.get());
                    notaFiscalCompraModeloDispositivo.setPrecoUnitario(item.getPrecoUnitario());
                    notaFiscalCompraModeloDispositivo.setQuantidade(item.getQuantidade());

                    itens.add(notaFiscalCompraModeloDispositivo);
                }
                if (itens.size() < 1) {
                    return ApiError.badRequest(Translator.toLocale("nota_fiscal_validacao_itens"));
                }

                //Atualizo os itens existentes ou adiciono os novos
                for (NotaFiscalCompraModeloDispositivo item : itens) {
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
                List<NotaFiscalCompraModeloDispositivo> itensRemover = new ArrayList();
                Iterator<NotaFiscalCompraModeloDispositivo> it = objeto.getItens().iterator();
                while (it.hasNext()) {
                    NotaFiscalCompraModeloDispositivo item = it.next();
                    if (!itens.contains(item)) {
                        itensRemover.add(item);
                    }
                }
                objeto.getItens().removeAll(itensRemover);

            }

            //Atualizo o objeto utilizando o repositório
            NotaFiscalCompra atualizado = notaFiscalRepository.save(objeto);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar uma nota fiscal de compra", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
