package api_teste.bryan_david.service;

import api_teste.bryan_david.Dto.TarefaRequestDTO;
import api_teste.bryan_david.Dto.TarefaResponseDTO;
import api_teste.bryan_david.model.Tarefa;
import api_teste.bryan_david.repository.TarefaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    // A injeção de dependência via construtor é a mais recomendada no Spring
    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public TarefaResponseDTO criar(TarefaRequestDTO dto) {
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setConcluida(false);

        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        return converter(tarefaSalva);
    }

    public List<TarefaResponseDTO> listar() {
        return tarefaRepository.findAll().stream()
                .map(this::converter)
                .collect(Collectors.toList());
    }

    public TarefaResponseDTO buscarPorId(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tarefa não encontrada"
                ));
        return converter(tarefa);
    }

    public TarefaResponseDTO atualizar(Long id, TarefaRequestDTO dto) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tarefa não encontrada"
                ));

        tarefa.setDescricao(dto.getDescricao());
        Tarefa tarefaAtualizada = tarefaRepository.save(tarefa);

        return converter(tarefaAtualizada);
    }

    public void excluir(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tarefa não encontrada"
                ));
        tarefaRepository.delete(tarefa);
    }

    private TarefaResponseDTO converter(Tarefa tarefa) {
        return new TarefaResponseDTO(
                tarefa.getId(),
                tarefa.getDescricao(),
                tarefa.isConcluida()
        );
    }
}