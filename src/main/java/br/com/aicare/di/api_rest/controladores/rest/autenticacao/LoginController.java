/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.autenticacao;

import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.configuracoes.seguranca.TokenProvider;
import br.com.aicare.di.api_rest.dominio.autenticacao.HistoricoLogin;
import br.com.aicare.di.api_rest.dominio.autenticacao.Perfil;
import br.com.aicare.di.api_rest.dto.autenticacao.LoginDTO;
import br.com.aicare.di.api_rest.dominio.autenticacao.Usuario;
import br.com.aicare.di.api_rest.dominio.pessoal.colaborador.Vinculado;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaFisica;
import br.com.aicare.di.api_rest.repository.autenticacao.HistoricoLoginRepository;
import br.com.aicare.di.api_rest.repository.autenticacao.UsuarioRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaFisicaRepository;
import br.com.aicare.di.api_rest.repository.pessoal.VinculadoRepository;
import br.com.aicare.di.api_rest.uteis.Senha;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    TokenProvider tokenProvider;
    
    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;
    
    @Autowired
    VinculadoRepository vinculadoRepository;
    
    @Autowired
    HistoricoLoginRepository historicoLoginRepository;
    
    @PostMapping()
    public ResponseEntity login(@RequestBody LoginDTO dto, HttpServletRequest request) {
        Map<Object, Object> model = new HashMap<>();
        
        Optional<Usuario> user = null;
        String userName = dto.getUsername();
        String metodoLogin = "";
        
        try {
            if (userName == null || userName.isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("login_informe_login"));
            }
            if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("login_informe_senha"));
            }

            //Tento logar pelo login
            user = usuarioRepository.findByLogin(userName);
            metodoLogin = "login: " + userName;

            //Se não encontrar, tento pelo CPF da pessoa associada
            if (!user.isPresent() && userName.matches("\\d+")) {
                PessoaFisica pessoa = pessoaFisicaRepository.findByCpf(userName);
                if (pessoa != null) {
                    user = usuarioRepository.findByPessoa(pessoa);
                    metodoLogin = "cpf: " + userName;
                }
            }

            //Se falhar tento pela matricula
            if (!user.isPresent()) {
                Vinculado vinculado = vinculadoRepository.findByMatricula(userName);
                if (vinculado != null) {
                    user = usuarioRepository.findByPessoa(vinculado.getPessoaFisica());
                    metodoLogin = "matricula: " + userName;
                }
            }
            
            if (user == null || !user.isPresent()) {
                logFalhaLogin(userName, "Username inválido", request);
                return ApiError.unauthorized(Translator.toLocale("login_credenciais_invalidas"));
            }
            
            Usuario usuario = user.get();
            
            Senha senha = new Senha(dto.getPassword());
            
            if (!senha.getHash().equals(usuario.getSenha())) {
                logFalhaLogin(userName, "Senha inválida", request);
                return ApiError.unauthorized(Translator.toLocale("login_credenciais_invalidas"));
            }
            
            if (!usuario.isHabilitado()) {
                logFalhaLogin(userName, "Usuário desabilitado", request);
                return ApiError.unauthorized(Translator.toLocale("login_usuario_desabilitado"));
            }
            
            List<String> authorities = new ArrayList<>();
            
            authorities.add("EMPTY");
            
            for (Perfil p : user.get().getPerfis()) {
                authorities.add(p.getCodigo().toUpperCase());
            }
            
            String token = tokenProvider.createToken(usuario.getLogin(), authorities);
            
            model.put("username", dto.getUsername());
            model.put("type", TokenProvider.TOKEN_PREFIX);
            model.put("token", token);
            
        } catch (Exception e) {
            LOGGER.error("Erro ao solicitar login", e);
            return ApiError.internalServerError(Translator.toLocale("erro_login"));
        }
        
        try {
            HistoricoLogin historicoLogin = HistoricoLogin.fabricar(request);
            historicoLogin.setUsuario(user.get());
            historicoLogin.setMetodo(metodoLogin);
            
            LOGGER.info("Login realizado com sucesso para " + userName + ". Método: " + metodoLogin + ". Detalhes: " + historicoLogin.getIP() + " - " + historicoLogin.getNavegador());
            
            historicoLoginRepository.save(historicoLogin);
        } catch (Exception e) {
            LOGGER.error("Erro ao persistir historio de login de " + dto.getUsername(), e);
        }
        
        return ok(model);
        
    }
    
    private void logFalhaLogin(String username, String motivo, HttpServletRequest request) {
        
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        String detalhes = ip + " - " + request.getHeader("User-Agent");
        
        LOGGER.error("Tentativa de login inválida para '" + username + "'. Motivo: " + motivo + ". Detalhes: " + detalhes);
    }
    
}
