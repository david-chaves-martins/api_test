package api_teste.bryan_david.sevice;

import api_teste.bryan_david.Dto.TarefaRequestDTO;
import api_teste.bryan_david.Dto.TarefaResponseDTO;
import api_teste.bryan_david.model.Tarefa;
import api_teste.bryan_david.repository.TarefaRepository;
import api_teste.bryan_david.service.TarefaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @Test
    void deveCriarTarefaComSucesso() {
        TarefaRequestDTO request = new TarefaRequestDTO();
        request.setDescricao("Nova Tarefa Mock");

        Tarefa tarefaSalva = new Tarefa();
        tarefaSalva.setId(1L);
        tarefaSalva.setDescricao("Nova Tarefa Mock");
        tarefaSalva.setConcluida(false);

        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaSalva);

        TarefaResponseDTO response = tarefaService.criar(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Nova Tarefa Mock", response.getDescricao());
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void deveBuscarTarefaPorIdComSucesso() {
        Long id = 1L;
        Tarefa tarefa = new Tarefa();
        tarefa.setId(id);
        tarefa.setDescricao("Estudar Spring");

        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));

        TarefaResponseDTO response = tarefaService.buscarPorId(id);

        assertEquals("Estudar Spring", response.getDescricao());
        verify(tarefaRepository, times(1)).findById(id);
    }

    @Test
    void deveLancarExcecaoAoBuscarTarefaInexistente() {
        Long id = 99L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tarefaService.buscarPorId(id)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(tarefaRepository, times(1)).findById(id);
    }

    @Test
    void deveExcluirTarefaComSucesso() {
        Long id = 1L;
        Tarefa tarefa = new Tarefa();
        tarefa.setId(id);

        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));
        doNothing().when(tarefaRepository).delete(tarefa);

        assertDoesNotThrow(() -> tarefaService.excluir(id));

        verify(tarefaRepository, times(1)).findById(id);
        verify(tarefaRepository, times(1)).delete(tarefa);
    }
}