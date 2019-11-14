/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.dispositivo;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivoCompartimento;
import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivoContainer;
import br.com.aicare.di.api_rest.dominio.evento.Interacao;
import br.com.aicare.di.api_rest.dominio.localizacao.Endereco;
import br.com.aicare.di.api_rest.dto.dispositivo.LocalizacaoDispositivoDTO;
import br.com.aicare.di.api_rest.repository.associacao.AssociacaoDispositivoCompartimentoRepository;
import br.com.aicare.di.api_rest.repository.associacao.AssociacaoDispositivoContainerRepository;
import br.com.aicare.di.api_rest.repository.associacao.AssociacaoDispositivoEquipamentoRepository;
import br.com.aicare.di.api_rest.repository.dispositivo.DispositivoRepository;
import br.com.aicare.di.api_rest.repository.evento.InteracaoRepository;
import br.com.aicare.di.api_rest.repository.localizacao.EnderecoRepository;
import br.com.aicare.di.api_rest.specifications.evento.InteracaoSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.ApiError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_ADM", "ROLE_GMN", "ROLE_GCM"})
@RequestMapping("/dispositivos/localizacao")
@Api(tags = "Localização de Dispositivos", description = "Localização de Dispositivo")
public class LocalizacaoDispositivoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DispositivoRepository dispositivoRepository;

    @Autowired
    AssociacaoDispositivoEquipamentoRepository associacaoDispositivoEquipamentoRepository;

    @Autowired
    AssociacaoDispositivoCompartimentoRepository associacaoDispositivoCompartimentoRepository;

    @Autowired
    AssociacaoDispositivoContainerRepository associacaoDispositivoContainerRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    InteracaoRepository interacaoRepository;

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    @ApiOperation(value = "Busca uma localização de um dispositivo pelo mac")
    @GetMapping(value = "/{mac}")
    public ResponseEntity<LocalizacaoDispositivoDTO> localizacao(@PathVariable String mac) {
        Optional<Dispositivo> dispositivo = dispositivoRepository.findByMac(mac);

        if (!dispositivo.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        Endereco endereco = null;

        if (dispositivo.get().getTipoDispositivo().isAssociavelLocalizacao()) {
            List<AssociacaoDispositivoCompartimento> associacaoDispositivoCompartimento = associacaoDispositivoCompartimentoRepository.obtemAssociacaoAtiva(dispositivo.get().getId());
            if (associacaoDispositivoCompartimento != null && !associacaoDispositivoCompartimento.isEmpty()) {
                endereco = associacaoDispositivoCompartimento.get(0).getCompartimento().getLocalizacaoFisica().getEndereco();
            }

            if (endereco == null) {//Para evitar consulta desnecessária
                List<AssociacaoDispositivoContainer> associacaoDispositivoContainer = associacaoDispositivoContainerRepository.obtemAssociacaoAtiva(dispositivo.get().getId());
                if (associacaoDispositivoContainer != null && !associacaoDispositivoContainer.isEmpty()) {
                    endereco = associacaoDispositivoContainer.get(0).getContainer().getCompartimento().getLocalizacaoFisica().getEndereco();
                }
            }
        }

        if (dispositivo.get().getTipoDispositivo().isAssociavelEquipamento()) {
            if (endereco == null) {//Para evitar consulta desnecessária
                Specification<Interacao> specification
                        = Specification.where(InteracaoSpecificationFactory.mac(mac))
                                .and(InteracaoSpecificationFactory.correntes());

                List<Interacao> interacoes = interacaoRepository.findAll(specification);

                for (Interacao interacao : interacoes) {
                    Dispositivo disp = interacao.getDispositivo1();
                    if (disp.getId() == dispositivo.get().getId()) {
                        disp = interacao.getDispositivo2();
                    }
                    if (disp.getTipoDispositivo().isAssociavelLocalizacao()) {
                        List<AssociacaoDispositivoCompartimento> associacaoDispositivoCompartimento = associacaoDispositivoCompartimentoRepository.obtemAssociacaoAtiva(disp.getId());
                        if (associacaoDispositivoCompartimento != null && !associacaoDispositivoCompartimento.isEmpty()) {
                            endereco = associacaoDispositivoCompartimento.get(0).getCompartimento().getLocalizacaoFisica().getEndereco();
                        }

                        if (endereco == null) {//Para evitar consulta desnecessária
                            List<AssociacaoDispositivoContainer> associacaoDispositivoContainer = associacaoDispositivoContainerRepository.obtemAssociacaoAtiva(disp.getId());
                            if (associacaoDispositivoContainer != null && !associacaoDispositivoContainer.isEmpty()) {
                                endereco = associacaoDispositivoContainer.get(0).getContainer().getCompartimento().getLocalizacaoFisica().getEndereco();
                            }
                        }
                    }
                }
            }
        }

        if (endereco == null) {
            return ApiError.notFound(Translator.toLocale("localizacao_nao_disponivel"));
        }

        LocalizacaoDispositivoDTO lddto = new LocalizacaoDispositivoDTO();
        lddto.setEndereco(endereco.getEnderecoFormatado());

        if (endereco.getLatitude() == null || endereco.getLongitude() == null) {
            try {
                final String uri = "https://maps.google.com/maps/api/geocode/json?address=" + endereco.getEnderecoFormatado() + "&key=" + googleMapsApiKey;

                RestTemplate restTemplate = new RestTemplate();
                String result = restTemplate.getForObject(uri, String.class);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(result);

                double lat = rootNode.get("results").get(0).get("geometry").get("location").get("lat").asDouble();
                double lon = rootNode.get("results").get(0).get("geometry").get("location").get("lng").asDouble();

                endereco.setLatitude(lat);
                endereco.setLongitude(lon);

                enderecoRepository.save(endereco);

            } catch (Exception ex) {
                LOGGER.error("Erro ao obter latitude/longitude do endereço", ex);
            }
        }

        if (endereco.getLatitude() != null && endereco.getLongitude() != null) {
            lddto.setGeoOk(true);
            lddto.setLatitude(endereco.getLatitude());
            lddto.setLongitude(endereco.getLongitude());
        }

        return new ResponseEntity<>(lddto, HttpStatus.OK);
    }

}
