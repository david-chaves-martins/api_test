package api_teste.bryan_david.Dto;

public class TarefaResponseDTO {

    private Long id;
    private String descricao;
    private boolean concluida;

    public TarefaResponseDTO(
            Long id,
            String descricao,
            boolean concluida) {

        this.id = id;
        this.descricao = descricao;
        this.concluida = concluida;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }
}
