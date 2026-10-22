package api_teste.bryan_david.controller; // Ajuste o pacote se necessário

import api_teste.bryan_david.Dto.TarefaRequestDTO;
import api_teste.bryan_david.model.Tarefa;
import api_teste.bryan_david.repository.TarefaRepository;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Limpa o banco em memória antes de cada teste para não haver conflitos
        tarefaRepository.deleteAll();
    }

    @Test
    void deveCriarTarefaERetornarStatus201() throws Exception {
        TarefaRequestDTO request = new TarefaRequestDTO();
        request.setDescricao("Tarefa de Integração");

        String jsonPayload = objectMapper.writeValueAsString(request);

        // Ajuste "/tarefas" para a rota real do seu Controller (ex: "/api/tarefas")
        mockMvc.perform(MockMvcRequestBuilders.post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isCreated()) // Ou isOk() se o seu controller retorna 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Tarefa de Integração"));
    }

    @Test
    void deveBuscarTarefaPorIdComSucesso() throws Exception {
        // Salva direto no banco para testar o GET
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao("Estudar MockMvc");
        tarefa.setConcluida(false);
        tarefa = tarefaRepository.save(tarefa);

        mockMvc.perform(MockMvcRequestBuilders.get("/tarefas/" + tarefa.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Estudar MockMvc"));
    }
}
