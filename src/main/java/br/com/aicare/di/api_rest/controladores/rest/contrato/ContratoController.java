/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.contrato;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.configuracoes.seguranca.TokenProvider;
import br.com.aicare.di.api_rest.dominio.autenticacao.Usuario;
import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuais;
import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuaisDispositivo;
import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuaisDispositivoPK;
import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuaisEquipamento;
import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuaisEquipamentoPK;
import br.com.aicare.di.api_rest.dominio.contrato.Contrato;
import br.com.aicare.di.api_rest.dominio.contrato.InsumoContratado;
import br.com.aicare.di.api_rest.dominio.contrato.InsumoContratadoPK;
import br.com.aicare.di.api_rest.dominio.contrato.LocalPlanejado;
import br.com.aicare.di.api_rest.dominio.contrato.LocalPlanejadoPK;
import br.com.aicare.di.api_rest.dominio.contrato.enums.TipoContrato;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaFisica;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import br.com.aicare.di.api_rest.dto.contrato.CondicoesContratuaisDTO;
import br.com.aicare.di.api_rest.dto.contrato.CondicoesContratuaisDispositivoDTO;
import br.com.aicare.di.api_rest.dto.contrato.CondicoesContratuaisEquipamentoDTO;
import br.com.aicare.di.api_rest.dto.contrato.ContratoDTO;
import br.com.aicare.di.api_rest.dto.contrato.ContratoSimpleResponseDTO;
import br.com.aicare.di.api_rest.dto.contrato.InsumoContratadoDTO;
import br.com.aicare.di.api_rest.dto.contrato.LocalizacaoObjetoDTO;
import br.com.aicare.di.api_rest.repository.autenticacao.UsuarioRepository;
import br.com.aicare.di.api_rest.repository.contrato.CondicoesContratuaisDispositivoRepository;
import br.com.aicare.di.api_rest.repository.contrato.CondicoesContratuaisEquipamentoRepository;
import br.com.aicare.di.api_rest.repository.contrato.CondicoesContratuaisRepository;
import br.com.aicare.di.api_rest.repository.contrato.ContratoRepository;
import br.com.aicare.di.api_rest.repository.contrato.InsumoContratadoRepository;
import br.com.aicare.di.api_rest.repository.contrato.LocalPlanejadoRepository;
import br.com.aicare.di.api_rest.repository.contrato.TipoInsumoRepository;
import br.com.aicare.di.api_rest.repository.dispositivo.ModeloDispositivoRepository;
import br.com.aicare.di.api_rest.repository.equipamento.ModeloEquipamentoRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaFisicaRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaJuridicaRepository;
import br.com.aicare.di.api_rest.repository.predial.LocalizacaoFisicaRepository;
import br.com.aicare.di.api_rest.specifications.contrato.ContratoSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.annotation.Secured;

@RestController
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_ADM"})
@RequestMapping("/contratos")
@Api(tags = "Contrato", description = "Contrato")
public class ContratoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());    

    @Autowired
    TokenProvider tokenProvider;
    
    @Autowired
    ContratoRepository contratoRepository;

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired 
    ModeloDispositivoRepository modeloDispositivoRepository;

    @Autowired
    ModeloEquipamentoRepository modeloEquipamentoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    LocalizacaoFisicaRepository localizacaoFisicaRepository;

    @Autowired
    TipoInsumoRepository tipoInsumoRepository;

    @Autowired
    CondicoesContratuaisRepository condicoesContratuaisRepository;

    @Autowired
    InsumoContratadoRepository insumoContratadoRepository;

    @Autowired
    CondicoesContratuaisDispositivoRepository condicoesContratuaisDispositivoRepository;

    @Autowired
    CondicoesContratuaisEquipamentoRepository condicoesContratuaisEquipamentoRepository;

    @Autowired
    LocalPlanejadoRepository localPlanejadoRepository;
   
    @ApiOperation(value = "Lista os Contratos")
    @GetMapping()
    public Page<Contrato> listar(
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

        LOGGER.info("Pesquisando lista de contratos.");

        Specification<Contrato> specification = Specification.where(null);

        if (q != null) {
            specification = specification.and(ContratoSpecificationFactory.busca(q));
        }
       
        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        Page<Contrato> results = contratoRepository.findAll(specification, pageable);
        return results;
    }


    @ApiOperation(value = "Lista os contratos com informações reduzidas")
    @GetMapping("/all")
    public Collection<ContratoSimpleResponseDTO> listar() {
        Collection<ContratoSimpleResponseDTO> all = contratoRepository.all();
        return all;
    }

    @ApiOperation(value = "Busca um contrato pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Contrato> buscar(@PathVariable Integer id) {
        Optional<Contrato> contrato = contratoRepository.findById(id);

        if (!contrato.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(contrato.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo contrato")
    @PostMapping()
    @Transactional 
    public ResponseEntity<Contrato> criar(
        @RequestBody ContratoDTO dto, 
        UriComponentsBuilder ucBuilder,
        @RequestHeader(name = "Authorization") String token) {

        try {
            LOGGER.info("Inserindo um novo contrato.");
            Contrato contrato = new Contrato();

            String username = tokenProvider.getUserFromToken(token);
            Optional<Usuario> usuario = usuarioRepository.findByLogin(username);
            contrato.setPessoaFisica(usuario.get().getPessoa());
            
            ResponseEntity error = configuraDadosBasicosContrato(contrato, dto);
            if (error != null) {
                return error;
            }
                        
            if (dto.getListaCondicoesContratuais() == null || dto.getListaCondicoesContratuais().size() <= 0 ){
                return ApiError.badRequest(Translator.toLocale("validacao_condicoes_contratuais"));
            }
            List<CondicoesContratuaisDTO> condicoesContratuais = dto.getListaCondicoesContratuais();            
            Set<CondicoesContratuais> condicoesContrato = new HashSet<>();
            int numeroAditivo = 0;

            for (CondicoesContratuaisDTO condicao : condicoesContratuais) {
                numeroAditivo += 1;
                ResponseEntity erro = configuraDadosCondicoesContratuais(contrato, condicoesContrato, condicao, numeroAditivo);
                if (erro != null) {
                    return erro;
                }
            }                        
            contrato.setCondicoesContratuais(condicoesContrato);

            Contrato novo = contratoRepository.save(contrato);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null)  return ApiError.badRequest("Ocorreu algum erro na criação desse objeto");
            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar um contrato", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }    

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "Atualiza um contrato")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Contrato> atualizar(@PathVariable("id") int id, @RequestBody ContratoDTO dto) {
        try {
            LOGGER.info("Atualizando contrato. ID = " + id);
            Optional<Contrato> contratoAtual = contratoRepository.findById(id);

            if (!contratoAtual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }
            Contrato contrato = contratoAtual.get();

            ResponseEntity error = configuraDadosBasicosContrato(contrato, dto);
            if (error != null) {
                return error;
            }
            
            if (dto.getListaCondicoesContratuais() == null || dto.getListaCondicoesContratuais().size() <= 0){
                return ApiError.badRequest(Translator.toLocale("validacao_condicoes_contratuais"));
            }
            List<CondicoesContratuaisDTO> condicoesContratuais = dto.getListaCondicoesContratuais();
            Set<CondicoesContratuais> condicoesContrato = new HashSet<>();
            
            int numeroAditivo = 0;
            for (CondicoesContratuaisDTO condicao : condicoesContratuais) {

                numeroAditivo += 1;
                ResponseEntity erro = configuraDadosCondicoesContratuais(contrato, condicoesContrato, condicao, numeroAditivo);
                if (erro != null) {
                    return erro;
                }
            
            }                        
            contrato.setCondicoesContratuais(condicoesContrato);
            Contrato novo = contratoRepository.save(contrato);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null)  return ApiError.badRequest("Ocorreu algum erro na alteração desse objeto");
            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar um contrato", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }
    
    private ResponseEntity configuraDadosBasicosContrato(Contrato contrato, ContratoDTO dto) {
        
        if (dto.getNumero() == null || dto.getNumero().equals("")) {
            return ApiError.badRequest(Translator.toLocale("validacao_numero_contrato"));
        }
        contrato.setNumero(dto.getNumero());

        PessoaJuridica pj = pessoaJuridicaRepository.findByCnpj( (dto.getContratante() != null ? dto.getContratante().getCnpj() : null) );
        if(pj  == null) {
            return ApiError.badRequest(Translator.toLocale("validacao_contratante"));
        }
        contrato.setContratante(pj);

        pj = pessoaJuridicaRepository.findByCnpj( ( dto.getContratado() != null ? dto.getContratado().getCnpj() : null) );
        if(pj == null) {
            return ApiError.badRequest(Translator.toLocale("validacao_contratado"));
        }
        contrato.setContratado(pj);

        PessoaFisica pf = pessoaFisicaRepository.findByCpf( ( dto.getContatoCliente() != null ? dto.getContatoCliente().getCpf() : null));
        if(pf == null) {
            return ApiError.badRequest(Translator.toLocale("validacao_contato_cliente"));
        }
        contrato.setContatoCliente(pf);

        pf = pessoaFisicaRepository.findByCpf( (dto.getContatoInterno() != null ? dto.getContatoInterno().getCpf() : null) );
        if (pf == null) {
            return ApiError.badRequest(Translator.toLocale("validacao_contato_interno"));
        }
        contrato.setContatoInterno(pf);

        if (dto.getTipoContrato() == null) {
            return ApiError.badRequest(Translator.toLocale("validacao_tipo_contrato"));
        }
        contrato.setTipoContrato(TipoContrato.valueOf(dto.getTipoContrato()));

        //contrato.setVinculadoHolding(dto.isVinculadoHolding());

        return null;
    }

    @Transactional
    private ResponseEntity configuraDadosCondicoesContratuais(Contrato contrato, Set<CondicoesContratuais> condicoesContrato, CondicoesContratuaisDTO condicao, int numeroAditivo) throws ParseException{
        
        CondicoesContratuais condicaoPrincipal = new CondicoesContratuais();
        
        if (condicao.getId() != null && contrato.getCondicoesContratuais() != null) {
            condicaoPrincipal = contrato
                .getCondicoesContratuais()
                .stream()
                .filter(c -> Integer.parseInt(condicao.getId()) ==  c.getId())
                .findFirst()
                .orElse(new CondicoesContratuais());           
        }
                
        //dados principais
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        Date date = sdf.parse(condicao.getDataAssinatura());
        condicaoPrincipal.setDataAssinatura(date);

        if (condicao.getDataEncerramento() != null && !condicao.getDataEncerramento().equals("")) {
            date = sdf.parse(condicao.getDataEncerramento());
            condicaoPrincipal.setDataEncerramento(date);
        } else {
            condicaoPrincipal.setDataEncerramento(null);
        }

        condicaoPrincipal.setContrato(contrato);
        condicaoPrincipal.setNumeroAditivo(numeroAditivo);
        ///////////////

        //lista localizacao
        if (condicao.getPossiveisLocais() == null || condicao.getPossiveisLocais().size() <= 0) {
            return ApiError.badRequest(Translator.toLocale("validacao_localizacao_planejada"));
        }
        Set<LocalizacaoObjetoDTO> listaLocalizacaoPlanejada = condicao.getPossiveisLocais();
        Set<LocalPlanejado> possiveisLocais = new HashSet<>();

        int quantidadeContratada = condicao.getSomaQuantidadeContratada();
        int somatorioQuantidade =  listaLocalizacaoPlanejada
                                    .stream()
                                    .reduce(0, (subtotal, item) -> subtotal + item.getQuantidadePlanejada(), Integer::sum);

        if ( somatorioQuantidade != quantidadeContratada) {
            return ApiError.badRequest(Translator.toLocale("erro_quantidade_diferente"));
        }
                
        for (LocalizacaoObjetoDTO localizacao : listaLocalizacaoPlanejada) {

            LocalPlanejado local =  new LocalPlanejado();
            LocalPlanejadoPK id = new LocalPlanejadoPK(condicaoPrincipal.getId(), localizacao.getLocalizacaoFisica().getId());
            Optional<LocalPlanejado> result = localPlanejadoRepository.findById(id);

            if (result.isPresent()) {
                local = result.get();
            }
            local.setLocalizacaoFisica(localizacaoFisicaRepository.findById(localizacao.getLocalizacaoFisica().getId()).get());
            local.setQuantidadePlanejada(localizacao.getQuantidadePlanejada());
            local.setCondicoesContratuais(condicaoPrincipal);

            if (possiveisLocais.contains(local)) {
                return ApiError.badRequest(Translator.toLocale("validacao_localizacao_planejada_duplicada"));
            }
            possiveisLocais.add(local);
        }

        if (condicaoPrincipal.getPossiveisLocais() != null) {
            condicaoPrincipal.getPossiveisLocais().clear();
            condicaoPrincipal.getPossiveisLocais().addAll(possiveisLocais);
        } else {
            condicaoPrincipal.setPossiveisLocais(possiveisLocais);
        }
        //////////
       
        //lista modelos        
        /*if(condicao.getModelosEquipamentosContratados()== null || condicao.getModelosEquipamentosContratados().size() <= 0) {
            return ApiError.badRequest(Translator.toLocale("validacao_modelos_equipamentos"));
        }*/

        if(condicao.getModelosEquipamentosContratados() != null) {
            Set<CondicoesContratuaisEquipamento> condicoesContratuaisEquipamento = new HashSet<>();
            for (CondicoesContratuaisEquipamentoDTO cceDTO : condicao.getModelosEquipamentosContratados()) {

                CondicoesContratuaisEquipamento cce = new CondicoesContratuaisEquipamento();
                CondicoesContratuaisEquipamentoPK id = new CondicoesContratuaisEquipamentoPK(condicaoPrincipal.getId(), cceDTO.getModeloEquipamento().getId());
                Optional<CondicoesContratuaisEquipamento> result = condicoesContratuaisEquipamentoRepository.findById(id);

                if (result.isPresent()) {
                    cce = result.get();
                }
                cce.setCondicoesContratuais(condicaoPrincipal);
                cce.setIntervaloCalibracao(cceDTO.getIntervaloCalibracao());
                cce.setIntervaloManutencaoNaoObrigatoria(cceDTO.getIntervaloManutencaoNaoObrigatoria());
                cce.setModeloEquipamento(modeloEquipamentoRepository.findById(cceDTO.getModeloEquipamento().getId()).get());
                cce.setPrecoAluguel(cceDTO.getPrecoAluguel());
                cce.setPrecoInstalacao(cceDTO.getPrecoInstalacao());
                cce.setPrecoManutencao(cceDTO.getPrecoManutencao());
                cce.setPrecoServico(cceDTO.getPrecoServico());
                cce.setPrecoVenda(cceDTO.getPrecoVenda());
                cce.setQuantidadeContratada(cceDTO.getQuantidadeContratada());
                cce.setTempoMaxKeepAlive(cceDTO.getTempoMaxKeepAlive());

                if (condicoesContratuaisEquipamento.contains(cce)) {
                    return ApiError.badRequest(Translator.toLocale("validacao_modelo_equipamento_duplicado"));
                }
                condicoesContratuaisEquipamento.add(cce);
            }

            if (condicaoPrincipal.getModelosEquipamentosContratados() != null) {
                condicaoPrincipal.getModelosEquipamentosContratados().clear();
                condicaoPrincipal.getModelosEquipamentosContratados().addAll(condicoesContratuaisEquipamento);
            } else {
                condicaoPrincipal.setModelosEquipamentosContratados(condicoesContratuaisEquipamento);  
            }    
        }    

    
        /*if (condicao.getModelosDispositivosContratados() == null || condicao.getModelosDispositivosContratados().size() <= 0) {
            return ApiError.badRequest(Translator.toLocale("validacao_modelos_dispositivos"));
        }*/
        
        if (condicao.getModelosDispositivosContratados() != null) {
            Set<CondicoesContratuaisDispositivo> condicoesContratuaisDispositivo = new HashSet<>();
            for (CondicoesContratuaisDispositivoDTO ccdDTO : condicao.getModelosDispositivosContratados()) {

                CondicoesContratuaisDispositivo ccd = new CondicoesContratuaisDispositivo();
                CondicoesContratuaisDispositivoPK id = new CondicoesContratuaisDispositivoPK(condicaoPrincipal.getId(), ccdDTO.getModeloDispositivo().getId());
                Optional<CondicoesContratuaisDispositivo> result = condicoesContratuaisDispositivoRepository.findById(id);

                if (result.isPresent()) {
                    ccd = result.get();
                }
                ccd.setCondicoesContratuais(condicaoPrincipal);
                ccd.setUidInstanceId(ccdDTO.getUidInstanceId());
                ccd.setModeloDispositivo(modeloDispositivoRepository.findById(ccdDTO.getModeloDispositivo().getId()).get());
                ccd.setPrecoAluguel(ccdDTO.getPrecoAluguel());
                ccd.setPrecoInstalacao(ccdDTO.getPrecoInstalacao());
                ccd.setPrecoManutencao(ccdDTO.getPrecoManutencao());
                ccd.setPrecoServico(ccdDTO.getPrecoServico());
                ccd.setPrecoVenda(ccdDTO.getPrecoVenda());
                ccd.setQuantidadeContratada(ccdDTO.getQuantidadeContratada());

                if (condicoesContratuaisDispositivo.contains(ccd)) {
                    return ApiError.badRequest(Translator.toLocale("validacao_modelo_dispositivo_duplicado"));
                }
                condicoesContratuaisDispositivo.add(ccd);
            }

            if (condicaoPrincipal.getModelosDispositivosContratados() != null) {
                condicaoPrincipal.getModelosDispositivosContratados().clear();
                condicaoPrincipal.getModelosDispositivosContratados().addAll(condicoesContratuaisDispositivo);
            } else {
                condicaoPrincipal.setModelosDispositivosContratados(condicoesContratuaisDispositivo);                    
            }
        }
        ////////////

        //lista insumos

        /*
        if (condicao.getInsumosCobertos() == null || condicao.getInsumosCobertos().size() <= 0) {
            return ApiError.badRequest(Translator.toLocale("validacao_insumos"));
        }
        */

        Set<InsumoContratado> insumosContratados = new HashSet<>();

        if (condicao.getInsumosCobertos() != null) {
            for (InsumoContratadoDTO icDTO : condicao.getInsumosCobertos()) {     
                
                InsumoContratado ic = new InsumoContratado();
                InsumoContratadoPK id = new InsumoContratadoPK(condicaoPrincipal.getId(), icDTO.getTipoInsumo().getId());
                Optional<InsumoContratado> result = insumoContratadoRepository.findById(id);
                
                if (result.isPresent()) {
                    ic = result.get();
                }
                ic.setCondicoesContratuais(condicaoPrincipal);
                ic.setDescricao(icDTO.getDescricao());
                ic.setPrecoContratado(icDTO.getPrecoContratado());
                ic.setQuantidadeMinima(icDTO.getQuantidadeMinima());
                ic.setTipoInsumo(tipoInsumoRepository.findById(icDTO.getTipoInsumo().getId()).get());

                if (insumosContratados.contains(ic)) {
                    return ApiError.badRequest(Translator.toLocale("validacao_insumo_duplicado"));
                }
                insumosContratados.add(ic);
            }
        }

        if (condicaoPrincipal.getInsumosCobertos() != null) {
            condicaoPrincipal.getInsumosCobertos().clear();
            condicaoPrincipal.getInsumosCobertos().addAll(insumosContratados);
        } else {
            condicaoPrincipal.setInsumosCobertos(insumosContratados);
        }
        ////////////////

        condicoesContrato.add(condicaoPrincipal);        
        return null;
    }

    @ApiOperation(value = "Lista os tipos de contrato")
    @GetMapping(value = "/tipos")
    public ResponseEntity<List<TipoContrato>> listarTiposContrato() {
        List<TipoContrato> tipos = new ArrayList<TipoContrato>();
        
        if (TipoContrato.values().length <= 0) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        for (int i = 0; i < TipoContrato.values().length; i++) {
                tipos.add(TipoContrato.values()[i]);
        }

        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }



}
