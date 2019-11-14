/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.testes.localizacao;

import br.com.aicare.di.api_rest.controladores.rest.localizacao.EstadoController;
import br.com.aicare.di.api_rest.dominio.localizacao.Estado;
import br.com.aicare.di.api_rest.repository.localizacao.EstadoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author FERNANDA
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
    EstadoController.class
})
public class EstadoTest {

    //URL base para acesso desse controlador
    private final String BASE_URL = "/localizacao/estado";

    //Instância do ObjectMapper para trabalhar com JSON
    private ObjectMapper objectMapper;

    //Controlador REST tratado por meio de injeção de dependências
    @Autowired
    private EstadoController restController;

    //Instância do MockMVC
    private MockMvc mockMvc;

    //Instância do mock repository
    @MockBean
    private EstadoRepository mockRepository;

    //////////////////////////////////////////////////
    private Estado estado;


    public EstadoTest() {

        estado = new Estado();
        estado.setId(1);
        estado.setNome("Rio de Janeiro");
        estado.setUf("RJ");
    }

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(restController)
                .build();
    }

    @Test
    public void buscar_id_200() throws Exception {

        when(mockRepository.findById(1)).thenReturn(Optional.of(estado));

        mockMvc.perform(get(BASE_URL + "/1"))
                /*.andDo(print())*/
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Rio de Janeiro")))
                .andExpect(jsonPath("$.uf", is("RJ")));

        verify(mockRepository, times(1)).findById(1);
    }

    @Test
    public void buscar_id_404() throws Exception {
        mockMvc.perform(get(BASE_URL + "/9")).andExpect(status().isNotFound());
    }

}
