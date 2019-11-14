/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.fatura;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.contrato.Contrato;
import br.com.aicare.di.api_rest.dominio.fatura.Fatura;
import br.com.aicare.di.api_rest.dominio.fatura.enums.Natureza;
import br.com.aicare.di.api_rest.dominio.fatura.itens.insumo.ItemFaturaInsumo;
import br.com.aicare.di.api_rest.dominio.fatura.itens.modelo_dispositivo.ItemFaturaModeloDispositivo;
import br.com.aicare.di.api_rest.dominio.fatura.itens.modelo_dispositivo.ItemFaturaModeloDispositivoPK;
import br.com.aicare.di.api_rest.dominio.fatura.itens.modelo_equipamento.ItemFaturaModeloEquipamento;
import br.com.aicare.di.api_rest.dominio.fatura.itens.modelo_equipamento.ItemFaturaModeloEquipamentoPK;
import br.com.aicare.di.api_rest.dominio.fatura.itens.tipo_insumo.ItemFaturaTipoInsumoPK;
import br.com.aicare.di.api_rest.dto.fatura.FaturaDTO;
import br.com.aicare.di.api_rest.repository.contrato.ContratoRepository;
import br.com.aicare.di.api_rest.repository.contrato.TipoInsumoRepository;
import br.com.aicare.di.api_rest.repository.dispositivo.ModeloDispositivoRepository;
import br.com.aicare.di.api_rest.repository.equipamento.ModeloEquipamentoRepository;
import br.com.aicare.di.api_rest.repository.fatura.FaturaRepository;
import br.com.aicare.di.api_rest.repository.fatura.ItemFaturaInsumoRepository;
import br.com.aicare.di.api_rest.repository.fatura.ItemFaturaModeloDispositivoRepository;
import br.com.aicare.di.api_rest.repository.fatura.ItemFaturaModeloEquipamentoRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaJuridicaRepository;
import br.com.aicare.di.api_rest.specifications.fatura.FaturaSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.annotation.Secured;

@RestController
@CrossOrigin
@Secured({"ROLE_ADA"})
@RequestMapping("/faturas")
@Api(tags = "Fatura", description = "Fatura")
public class FaturaController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());    

    @Autowired
    FaturaRepository faturaRepository;

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    ContratoRepository contratoRepository;

    @Autowired
    TipoInsumoRepository tipoInsumoRepository;

    @Autowired
    ModeloDispositivoRepository modeloDispositivoRepository;

    @Autowired
    ModeloEquipamentoRepository modeloEquipamentoRepository;

    @Autowired
    ItemFaturaModeloDispositivoRepository itemFaturaModeloDispositivoRepository;

    @Autowired
    ItemFaturaModeloEquipamentoRepository itemFaturaModeloEquipamentoRepository;

    @Autowired
    ItemFaturaInsumoRepository itemFaturaInsumoRepository;

    @ApiOperation(value = "Lista as Faturas")
    @GetMapping()
    public Page<Fatura> listar(
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

        LOGGER.info("Pesquisando lista de faturas.");

        Specification<Fatura> specification = Specification.where(null);

        if (q != null) {
            specification = specification.and(FaturaSpecificationFactory.busca(q));
        }
       
        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        Page<Fatura> results = faturaRepository.findAll(specification, pageable);
        return results;
    }


    @ApiOperation(value = "Busca uma fatura pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Fatura> buscar(@PathVariable Integer id) {
        Optional<Fatura> fatura = faturaRepository.findById(id);

        if (!fatura.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(fatura.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Lista os tipos de natureza de fatura")
    @GetMapping(value = "/natureza")
    public ResponseEntity<List<Natureza>> listarNatureza() {
        List<Natureza> tipos = new ArrayList<Natureza>();
        
        if (Natureza.values().length <= 0) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        for (int i = 0; i < Natureza.values().length; i++) {
                tipos.add(Natureza.values()[i]);
        }

        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    @ApiOperation(value = "Cria uma nova fatura")
    @PostMapping()
    @Transactional 
    public ResponseEntity<Fatura> criar(
        @RequestBody FaturaDTO dto, 
        UriComponentsBuilder ucBuilder) {

        try{

            LOGGER.info("Inserindo nova fatura");
            Fatura fatura = new Fatura();

            ResponseEntity error = configuraDadosBasicosFatura(fatura, dto);
            if (error != null) {
                return error;
            }

            ResponseEntity erro = configuraListaItens(fatura, dto);
            if (erro != null) {
                return erro;
            }

            Fatura novo = faturaRepository.save(fatura);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null)  return ApiError.badRequest("Ocorreu algum erro na criação desse objeto");
            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar uma fatura", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Atualiza uma fatura")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Fatura> atualizar(@PathVariable("id") int id, @RequestBody FaturaDTO dto) {
        try {
            LOGGER.info("Atualizando fatura. ID = " + id);
            Optional<Fatura> faturaAtual = faturaRepository.findById(id);

            if (!faturaAtual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }
            Fatura fatura = faturaAtual.get();

            ResponseEntity error = configuraDadosBasicosFatura(fatura, dto);
            if (error != null) {
                return error;
            }

            ResponseEntity erro = configuraListaItens(fatura, dto);
            if (erro != null) {
                return erro;
            }
            
            Fatura novo = faturaRepository.save(fatura);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null)  return ApiError.badRequest("Ocorreu algum erro na alteração desse objeto");
            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar uma fatura", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

    private ResponseEntity configuraDadosBasicosFatura(Fatura fatura, FaturaDTO dto) throws ParseException {

        if (dto.getNumeroNotaFiscal() == null || dto.getNumeroNotaFiscal().equals("")) {
            return ApiError.badRequest(Translator.toLocale("validacao_numero_nota_fiscal"));
        }
        fatura.setNumeroNotaFiscal(dto.getNumeroNotaFiscal());

        if(dto.getNatureza() == null || dto.getNatureza().equals("")) {
            return ApiError.badRequest(Translator.toLocale("validacao_natureza_fatura"));
        }
        fatura.setNatureza( Natureza.valueOf(dto.getNatureza()));

        if (dto.getData() == null || dto.getData().equals("")) {
            return ApiError.badRequest(Translator.toLocale("validacao_data_fatura"));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        Date date = sdf.parse(dto.getData());
        fatura.setData(date);

        if (dto.getPessoaJuridica() == null) {
            return ApiError.badRequest(Translator.toLocale("validacao_cliente_faturado"));
        }
        fatura.setPessoaJuridica(pessoaJuridicaRepository.findByCnpj(dto.getPessoaJuridica().getCnpj()));
        
        if (dto.getContrato() != null) {
            Optional<Contrato> contrato = contratoRepository.findById(dto.getContrato().getId());

            if(contrato.isPresent()) {
                Set<Contrato> lista = new HashSet<>();
                lista.add(contrato.get());
                fatura.setContratos(lista);
            }
        }

        fatura.setDescricao(dto.getDescricao());

        return null;
    }

    private ResponseEntity configuraListaItens(Fatura fatura, FaturaDTO dto) {
        boolean isItensInsumos = false;
        boolean isItensDispositivos = false;
        boolean isItensEquipamentos = false;
        
        
        Set<ItemFaturaInsumo> listaInsumo = new HashSet<>();
        if (dto.getItensInsumo() != null && dto.getItensInsumo().size() > 0) {
            isItensInsumos = true;
        
            dto.getItensInsumo().forEach(insumo -> {
                ItemFaturaTipoInsumoPK id = new ItemFaturaTipoInsumoPK(fatura.getId(),insumo.getTipoInsumo().getId());
                ItemFaturaInsumo item = new ItemFaturaInsumo();
                Optional<ItemFaturaInsumo> result = itemFaturaInsumoRepository.findById(id);

                if (result.isPresent()) {
                    item = result.get();
                }

                item.setFatura(fatura);
                item.setPrecoUnitario(insumo.getPrecoUnitario());
                item.setQuantidade(insumo.getQuantidade());
                item.setTipoInsumo(tipoInsumoRepository.findById(insumo.getTipoInsumo().getId()).get());
                listaInsumo.add(item);
            });
        }

        if (fatura.getId() > 0 && dto.getItensInsumo() != null) {
            fatura.getItensInsumo().clear();
            fatura.getItensInsumo().addAll(listaInsumo);            
        } else if (fatura.getId() <= 0 && listaInsumo.size() > 0) {
            fatura.setItensInsumo(listaInsumo); 
        }
                          
        Set<ItemFaturaModeloDispositivo> listaModeloDispositivo = new HashSet<>();
        if (dto.getItensModeloDispositivo() != null && dto.getItensModeloDispositivo().size() > 0) {
            isItensDispositivos = true;
       
            dto.getItensModeloDispositivo().forEach(modelo -> {
                ItemFaturaModeloDispositivoPK id = new ItemFaturaModeloDispositivoPK(fatura.getId(),modelo.getModeloDispositivo().getId());
                ItemFaturaModeloDispositivo item = new ItemFaturaModeloDispositivo();
                Optional<ItemFaturaModeloDispositivo> result = itemFaturaModeloDispositivoRepository.findById(id);

                if(result.isPresent()) {
                    item = result.get();
                }

                item.setFatura(fatura);
                item.setPrecoUnitario(modelo.getPrecoUnitario());
                item.setQuantidade(modelo.getQuantidade());
                item.setModeloDispositivo(modeloDispositivoRepository.findById(modelo.getModeloDispositivo().getId()).get());
                listaModeloDispositivo.add(item);
            });
        }

        if (fatura.getId() > 0 && dto.getItensModeloDispositivo() != null) {
            fatura.getItensModeloDispositivo().clear();
            fatura.getItensModeloDispositivo().addAll(listaModeloDispositivo);
        }  else if (fatura.getId() <= 0 && listaModeloDispositivo.size() > 0) {
            fatura.setItensModeloDispositivo(listaModeloDispositivo);
        }

        
       
        Set<ItemFaturaModeloEquipamento> listaModeloEquipamento = new HashSet<>();
        if (dto.getItensModeloEquipamento() != null && dto.getItensModeloEquipamento().size() > 0) {
            isItensEquipamentos = true;
        
            dto.getItensModeloEquipamento().forEach(modelo -> {
                ItemFaturaModeloEquipamentoPK id = new ItemFaturaModeloEquipamentoPK(fatura.getId(),modelo.getModeloEquipamento().getId());
                ItemFaturaModeloEquipamento item = new ItemFaturaModeloEquipamento();
                Optional<ItemFaturaModeloEquipamento> result = itemFaturaModeloEquipamentoRepository.findById(id);

                if(result.isPresent()) {
                    item = result.get();
                }

                item.setFatura(fatura);
                item.setPrecoUnitario(modelo.getPrecoUnitario());
                item.setQuantidade(modelo.getQuantidade());
                item.setModeloEquipamento(modeloEquipamentoRepository.findById(modelo.getModeloEquipamento().getId()).get());
                listaModeloEquipamento.add(item);
            });
        }

        if (fatura.getId() > 0 && dto.getItensModeloEquipamento() != null) {
            fatura.getItensModeloEquipamento().clear();
            fatura.getItensModeloEquipamento().addAll(listaModeloEquipamento);
        } else if (fatura.getId() <= 0 && listaModeloEquipamento.size() > 0) {     
            fatura.setItensModeloEquipamento(listaModeloEquipamento);
        }
        
        if (!isItensInsumos && !isItensDispositivos && !isItensEquipamentos) {
            return ApiError.badRequest(Translator.toLocale("validacao_itens_fatura"));
        }

        return null;
    }
}