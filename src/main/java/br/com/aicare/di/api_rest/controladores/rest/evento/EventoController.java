/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.evento;

import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import br.com.aicare.di.api_rest.dominio.dispositivo.TipoDispositivo;
import br.com.aicare.di.api_rest.dominio.evento.Evento;
import br.com.aicare.di.api_rest.dominio.evento.Interacao;
import br.com.aicare.di.api_rest.dto.evento.EventoDTO;
import br.com.aicare.di.api_rest.repository.dispositivo.DispositivoRepository;
import br.com.aicare.di.api_rest.repository.dispositivo.EstadoAtualRepository;
import br.com.aicare.di.api_rest.repository.dispositivo.TipoDispositivoRepository;
import br.com.aicare.di.api_rest.repository.evento.EventoRepository;
import br.com.aicare.di.api_rest.repository.evento.InteracaoRepository;
import br.com.aicare.di.api_rest.specifications.evento.EventoSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_PEV"})
@RequestMapping("/eventos")
@Api(tags = "Eventos", description = "Eventos")
public class EventoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //Códigos de eventos
    private final static String IID = "IID";//inicio de interação de dispositivo
    private final static String FID = "FID";//fim de interação de dispositivo
    private final static String KPA = "KPA";//keepalive de dispositivo
    private final static String WTM = "WTM";//worktime de dispositivo
    private final static String ACC = "ACC";//acelerometro de dispositivo
    private final static String TMP = "TMP";//temperatura de dispositivo
    private final static String BTR = "BTR";//bateria de dispositivo
    private final static String TID = "TID";//tipo de dispositivo
    private final static String NSI = "NSI";//namespace de dispositivo

    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    DispositivoRepository dispositivoRepository;

    @Autowired
    InteracaoRepository interacaoRepository;

    @Autowired
    EstadoAtualRepository estadoAtualRepository;

    @Autowired
    TipoDispositivoRepository tipoDispositivoRepository;

    @ApiOperation(value = "Lista todos os eventos")
    @GetMapping
    public Page<Evento> listar(
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size,
            @RequestParam(
                    value = "sort",
                    required = false) String sort,
            @ApiParam(required = false, name = "t", value = "Filtra pelo tipo do evento")
            @RequestParam(
                    value = "t",
                    required = false) String t,
            @ApiParam(required = false, name = "mac", value = "Filtra por MAC")
            @RequestParam(
                    value = "mac",
                    required = false) String mac,
            @ApiParam(required = false, name = "q", value = "Busca por mac ou tipo")
            @RequestParam(
                    value = "q",
                    required = false) String q,
            @ApiParam(required = false, name = "gt", value = "Data maior ou igual (milisegundos desde 01/01/1970, 00:00:00 GMT)")
            @RequestParam(
                    value = "gt",
                    required = false) String gt,
            @ApiParam(required = false, name = "lt", value = "Data menor ou igual (milisegundos desde 01/01/1970, 00:00:00 GMT)")
            @RequestParam(
                    value = "lt",
                    required = false) String lt
    ) {

        Specification<Evento> specification = Specification.where(null);

        if (t != null) {
            specification = specification.and(EventoSpecificationFactory.tipo(t));
        }

        if (mac != null) {
            specification = specification.and(EventoSpecificationFactory.mac(mac));
        }

        if (q != null) {
            specification = specification.and(EventoSpecificationFactory.busca(q));
        }

        if (gt != null) {
            specification = specification.and(EventoSpecificationFactory.dataHoraMaiorOuIgualQue(new Date(Long.parseLong(gt))));
        }

        if (lt != null) {
            specification = specification.and(EventoSpecificationFactory.dataHoraMenorOuIgualQue(new Date(Long.parseLong(lt))));
        }

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        Page<Evento> resultPage = eventoRepository.findAll(specification, pageable);

        return resultPage;
    }

    /**
     * post para persistir um evento, deve ver se é de interação e persistir em
     * interações tb
     *
     * @param dto
     * @return
     */
    @PostMapping()
    @ApiOperation(value = "Salva um evento",
            notes = "Salva um evento aicare, se esse for uma interação, salva também como a mesma")
    public ResponseEntity evento(
            @RequestBody EventoDTO dto
    ) {

        //Obtenho os campos da requisição
        String mac = dto.getMac();
        String tipo = dto.getTipo().toUpperCase();
        String dado = dto.getDado();

        Dispositivo dispositivo1 = null;
        Dispositivo dispositivo2 = null;

        //Obtenho o dispositivo do banco
        Optional<Dispositivo> optional = dispositivoRepository.findByMac(mac);

        //Se seu identificador (mac) for inválido ou inesistente, não continuo
        if (!optional.isPresent()) {
            return ApiError.badRequest("MAC inválido ou não encontrado: " + mac);
        }

        Date date = new Date();

        dispositivo1 = optional.get();

        try {
            //Preparo um objeto evento
            Evento evento = new Evento();
            evento.setDataHora(date);
            evento.setTipo(tipo);
            evento.setDado(dado);
            evento.setDispositivo(dispositivo1);

            //Persisto o evento
            eventoRepository.save(evento);
        } catch (Exception e) {
            LOGGER.error("Erro ao salvar um evento", e);
        }

        try {
            //Verifico se o dado é um dispositivo, se sim, persisto outro evento para ele
            if (dado != null && dado.matches("([a-fA-F0-9]){12}")) {//regex do MAC

                Optional<Dispositivo> optionalDado = dispositivoRepository.findByMac(dado);

                //Se o dispositivo existe, persisto a associativa
                if (optionalDado.isPresent()) {
                    dispositivo2 = optionalDado.get();

                    Evento evento2 = new Evento();
                    evento2.setDataHora(date);
                    evento2.setTipo(tipo);
                    evento2.setDado(dispositivo1.getMac());//uso o mac do dispositivo que gerou o evento para persistir esse novo
                    evento2.setDispositivo(dispositivo2);

                    //Persisto o evento
                    eventoRepository.save(evento2);

                }
            }
        } catch (Exception e) {
            LOGGER.error("Erro ao salvar um evento gerado a partir de um dado", e);
        }

        ////////////////////////////////////////////////////////////////////////
        /// Eventos de interação
        ////////////////////////////////////////////////////////////////////////
        try {
            if (dispositivo2 != null) {

                if (tipo.equals(IID)) { //Se o evento for do tipo inicio de interação, preciso persistir na tabela de interações

                    //Busco se há alguma interação corrente, se sim, não crio uma novamente
                    List<Interacao> interacoesCorrentes = interacaoRepository.obtemInteracaoCorrente(dispositivo1.getId(), dispositivo2.getId());
                    if (interacoesCorrentes == null || interacoesCorrentes.isEmpty()) {
                        //Crio um objeto interação com a data de inicio atual
                        Interacao interacao = new Interacao();
                        interacao.setInicio(date);
                        interacao.setDispositivo1(dispositivo1);
                        interacao.setDispositivo2(dispositivo2);

                        //Persisto a interação e as associativas relacionando com os dois dispositivos desse enevto
                        interacaoRepository.save(interacao);
                    }

                } else if (tipo.equals(FID)) {//Se o evento for do tipo fim de interação, preciso obter a interação ativa e definir a data de fim atual

                    //Obtenho a interação corrente para esse conjunto de dispositivos
                    List<Interacao> interacoes = interacaoRepository.obtemInteracaoCorrente(dispositivo1.getId(), dispositivo2.getId());
                    if (interacoes != null && !interacoes.isEmpty()) {
                        Interacao interacao = interacoes.get(0);
                        interacao.setFim(date);

                        //Atualizo a interação já existente
                        interacaoRepository.save(interacao);

                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Erro ao salvar uma interação", e);
        }

        ////////////////////////////////////////////////////////////////////////
        /// Eventos de TLM e outros eventos
        ////////////////////////////////////////////////////////////////////////
        try {
            switch (tipo) {
                case KPA:
                    dispositivo1.getEstadoAtual().setVistoEm(date);
                    estadoAtualRepository.save(dispositivo1.getEstadoAtual());
                    break;
                case BTR:
                    if (dado != null) {
                        int bateria = Integer.parseInt(dado);
                        dispositivo1.getEstadoAtual().setBateria(bateria);
                        estadoAtualRepository.save(dispositivo1.getEstadoAtual());
                    }
                    break;
                case WTM:
                    if (dado != null) {
                        int worktime = Integer.parseInt(dado);
                        dispositivo1.getEstadoAtual().setWorkTime(worktime);
                        estadoAtualRepository.save(dispositivo1.getEstadoAtual());
                    }
                    break;
                case TMP:
                    if (dado != null) {
                        float temperatura = Float.parseFloat(dado);
                        dispositivo1.getEstadoAtual().setTemperatura(temperatura);
                        estadoAtualRepository.save(dispositivo1.getEstadoAtual());
                    }
                    break;
                case ACC:
                    if (dado != null) {
                        String p[] = dado.split(",");
                        if (p.length == 3) {
                            float x = Float.parseFloat(p[0]);
                            float y = Float.parseFloat(p[1]);
                            float z = Float.parseFloat(p[2]);

                            dispositivo1.getEstadoAtual().setEixoX(x);
                            dispositivo1.getEstadoAtual().setEixoY(y);
                            dispositivo1.getEstadoAtual().setEixoZ(z);

                            estadoAtualRepository.save(dispositivo1.getEstadoAtual());
                        }
                    }
                    break;
                case TID:
                    if (dado != null) {
                        Optional<TipoDispositivo> tipoDispositivo = tipoDispositivoRepository.findByCodigo(dado);
                        if (tipoDispositivo.isPresent()) {
                            dispositivo1.setTipoDispositivo(tipoDispositivo.get());
                            dispositivoRepository.save(dispositivo1);
                        }
                    }
                    break;
                case NSI:
                    if (dado != null) {
                        dispositivo1.setNameSpace(dado);
                        dispositivoRepository.save(dispositivo1);
                    }
                    break;
            }
        } catch (Exception e) {
            LOGGER.error("Erro ao salvar um evento que gerou um dado em estado atual", e);
        }

        Map<Object, Object> model = new HashMap<>();
        return ok(model);
    }
}
