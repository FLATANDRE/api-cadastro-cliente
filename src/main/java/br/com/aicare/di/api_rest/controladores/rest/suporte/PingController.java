/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.suporte;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador simples para responder a verificação se a api esta online
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@RequestMapping("/ping")
public class PingController {

    @GetMapping()
    public ResponseEntity ping() {
        Map<Object, Object> model = new HashMap<>();
        model.put("ping", "aicare");
        model.put("timestamp", System.currentTimeMillis());
        return ok(model);
    }

}
