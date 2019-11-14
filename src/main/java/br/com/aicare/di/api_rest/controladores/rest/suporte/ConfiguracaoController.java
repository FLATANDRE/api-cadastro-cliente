/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.suporte;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.configuracao.Configuracao;
import br.com.aicare.di.api_rest.dto.configuracao.ConfiguracaoPublicDTO;
import br.com.aicare.di.api_rest.repository.suporte.ConfiguracaoRepository;
import br.com.aicare.di.api_rest.uteis.ApiError;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@RequestMapping("/config")
public class ConfiguracaoController {

    @Autowired
    ConfiguracaoRepository configuracaoRepository;

    @GetMapping()
    public ResponseEntity<Configuracao> privado() {

        Optional<Configuracao> configuracao = configuracaoRepository.findById(1);

        if (!configuracao.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(configuracao.get(), HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<ConfiguracaoPublicDTO> publico() {

        Collection<ConfiguracaoPublicDTO> configuracao = configuracaoRepository.getPublic(1);

        if (configuracao.isEmpty()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(configuracao.iterator().next(), HttpStatus.OK);
    }

}
