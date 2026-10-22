package api_teste.bryan_david.Dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TarefaRequestDTO {

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

}