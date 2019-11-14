/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.relatorio;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.equipamento.ModeloEquipamento;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import br.com.aicare.di.api_rest.dto.relatorio.contrato.ContratoMapaDTO;
import br.com.aicare.di.api_rest.repository.equipamento.ModeloEquipamentoRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaJuridicaRepository;
import br.com.aicare.di.api_rest.uteis.ApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_ADM", "ROLE_GMN", "ROLE_GCM"})
@RequestMapping("/relatorios/contratos/mapas")
@Api(tags = "Relatório de contratos por mapa", description = "Relatório de contratos por mapa")
public class RelatorioContratosMapaController {

    private List<ContratoMapaDTO> contratosTemp;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    ModeloEquipamentoRepository modeloEquipamentoRepository;

    public void constroi() {
        if (contratosTemp != null) {
            return;
        }
        System.out.println("construindo");
        contratosTemp = new ArrayList<>();

        Optional<ModeloEquipamento> modelo = modeloEquipamentoRepository.findById(2);

        Iterator<PessoaJuridica> pjs = pessoaJuridicaRepository.findAll().iterator();

        while (pjs.hasNext()) {
            PessoaJuridica pj = pjs.next();
            if (pj.getId() == 1) {
                continue;//ignora a AIcare
            }

            ContratoMapaDTO cmdto = new ContratoMapaDTO();

            cmdto.setPessoaJuridica(pj);

            if (pj.getId() == 2) {
                cmdto.setStatus(1);
                cmdto.setNumeroContrato("563278567384");
                cmdto.setEquipos(176);
                cmdto.setPreco(19.86);
                cmdto.setBombas(2);
                cmdto.setModeloEquipamento(modelo.get());
                cmdto.setQuantidadeEquipamentosContratados(867);
                cmdto.setQuantidadeEquiposContratados(6069);
                cmdto.setMacGateway("AC233FC0370C");
                cmdto.setDataContrato("21/09/2019");
                cmdto.setDiasParada(9);
                cmdto.setObrigatoriaVencida(2);
                cmdto.setChamadoAberto(6);
                cmdto.setDataVencimentoContrato("12/12/2019");
                cmdto.setPerformanceAno(95);
                cmdto.setStatusRenovacaoContrato(false);
            }
            if (pj.getId() == 3) {
                cmdto.setStatus(-1);
                cmdto.setNumeroContrato("792348756923");
                cmdto.setEquipos(-1963);
                cmdto.setPreco(20.55);
                cmdto.setBombas(-23);
                cmdto.setModeloEquipamento(modelo.get());
                cmdto.setQuantidadeEquipamentosContratados(867);
                cmdto.setQuantidadeEquiposContratados(6069);
                cmdto.setMacGateway("AC233FC036FA");
                cmdto.setDataContrato("21/09/2019");
                cmdto.setDiasParada(0);
                cmdto.setObrigatoriaVencida(5);
                cmdto.setChamadoAberto(2);
                cmdto.setDataVencimentoContrato("01/11/2019");
                cmdto.setPerformanceAno(105);
                cmdto.setStatusRenovacaoContrato(true);
            }

            contratosTemp.add(cmdto);

        }
    }

    @ApiOperation(value = "Obtém o relatório de contratos")
    @GetMapping()
    public ResponseEntity<List<ContratoMapaDTO>> contratos() {
        constroi();
        return new ResponseEntity<>(contratosTemp, HttpStatus.OK);

    }

    @ApiOperation(value = "Busca um relatorio pelo numero do contrato")
    @GetMapping(value = "/{numero}")
    public ResponseEntity<ContratoMapaDTO> listar(@PathVariable String numero) {

        constroi();

        for (ContratoMapaDTO dto : contratosTemp) {
            if (dto.getNumeroContrato().equals(numero)) {
                return new ResponseEntity<>(dto, HttpStatus.OK);
            }
        }

        return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
    }

}
