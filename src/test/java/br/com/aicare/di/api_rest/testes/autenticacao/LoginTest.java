/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.testes.autenticacao;

import br.com.aicare.di.api_rest.controladores.rest.autenticacao.LoginController;
import br.com.aicare.di.api_rest.dominio.autenticacao.HistoricoLogin;
import br.com.aicare.di.api_rest.dto.autenticacao.LoginDTO;
import br.com.aicare.di.api_rest.dominio.autenticacao.Usuario;
import br.com.aicare.di.api_rest.repository.autenticacao.HistoricoLoginRepository;
import br.com.aicare.di.api_rest.repository.autenticacao.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author Paulo Collares
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {
//    LoginController.class
//})
public class LoginTest {

//    //URL base para acesso desse controlador
//    private final String BASE_URL = "/login";
//
//    //Instância do ObjectMapper para trabalhar com JSON
//    private ObjectMapper objectMapper;
//
//    //Controlador REST tratado por meio de injeção de dependências
//    @Autowired
//    private LoginController restController;
//
//    //Instância do MockMVC
//    private MockMvc mockMvc;
//
//    //Instância do mock repository
//    @MockBean
//    private UsuarioRepository mockRepository;
//    @MockBean
//    private HistoricoLoginRepository historicoLoginRepository;
//
//    public LoginTest() {
//
//    }
//
//    @Before
//    public void setUp() {
//        objectMapper = new ObjectMapper();
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(restController)
//                .build();
//
//        Usuario usuario = new Usuario();
//        usuario.setId(1);
//        usuario.setLogin("teste");
//        usuario.setSenha("Q49twsPgvzjcYbGb6vV6RgdIy8pfANntyqxBcN0djWYjbMXyiBHOv559IUmLZG1Ne6YP3UsiR+92NiJYzBAt4A==");
//        usuario.setHabilitado(true);
//        usuario.setDataCriacao(new Date(2019, 5, 22));
//
//        Usuario usuario2 = new Usuario();
//        usuario2.setId(2);
//        usuario2.setLogin("teste2");
//        usuario2.setSenha("j29$Tp0X#LZ#^uPBd6%ta");
//        usuario2.setHabilitado(false);
//        usuario2.setDataCriacao(new Date(2019, 5, 22));
//
//        when(mockRepository.findByLogin("teste")).thenReturn(Optional.of(usuario));
//        when(mockRepository.findByLogin("teste2")).thenReturn(Optional.of(usuario2));
//
//        when(historicoLoginRepository.save(any(HistoricoLogin.class))).thenReturn(new HistoricoLogin());
//
//    }

    @Test
    public void teste() {

    }

//    @Test
//    public void deveEfetuarLogin() throws Exception {
//
//        LoginDTO login = new LoginDTO();
//        login.setUsername("teste");
//        login.setPassword("12345678");
//
//        mockMvc.perform(post(BASE_URL)
//                .content(objectMapper.writeValueAsString(login))
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username", is("teste")))
//                .andExpect(jsonPath("$.token", notNullValue()))
//                .andExpect(jsonPath("$.type", is("Bearer")));
//
//        verify(mockRepository, times(1)).findByLogin("teste");
//        verify(historicoLoginRepository, times(1)).save(any(HistoricoLogin.class));
//    }
//
//    @Test
//    public void deveRetornarErroComUsuarioErrado() throws Exception {
//
//        LoginDTO login = new LoginDTO();
//        login.setUsername("testando");
//        login.setPassword("123");
//
//        mockMvc.perform(post(BASE_URL)
//                .content(objectMapper.writeValueAsString(login))
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andExpect(status().is4xxClientError());
//
//        verify(mockRepository, times(1)).findByLogin("testando");
//    }
//
//    @Test
//    public void deveRetornarErroComSenhaErrada() throws Exception {
//
//        LoginDTO login = new LoginDTO();
//        login.setUsername("teste");
//        login.setPassword("senhaerrada");
//
//        mockMvc.perform(post(BASE_URL)
//                .content(objectMapper.writeValueAsString(login))
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andExpect(status().is4xxClientError());
//
//        verify(mockRepository, times(1)).findByLogin("teste");
//    }
//
//    @Test
//    public void deveRetornarErroUsuarioNaoHabilitado() throws Exception {
//
//        LoginDTO login = new LoginDTO();
//        login.setUsername("teste2");
//        login.setPassword("j29$Tp0X#LZ#^uPBd6%ta");
//
//        mockMvc.perform(post(BASE_URL)
//                .content(objectMapper.writeValueAsString(login))
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andExpect(status().is4xxClientError());
//
//        verify(mockRepository, times(1)).findByLogin("teste2");
//    }
}
