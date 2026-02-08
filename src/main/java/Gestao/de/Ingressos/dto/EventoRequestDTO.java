package Gestao.de.Ingressos.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequestDTO {

    @NotBlank(message = "O nome do evento é obrigatório")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "A data do evento é obrigatória")
    @Future(message = "A data do evento deve ser no futuro")
    private LocalDateTime dataEvento;

    @NotBlank(message = "O local é obrigatório")
    private String local;

    @NotNull(message = "A capacidade é obrigatória")
    @Min(value = 1, message = "A capacidade deve ser de pelo menos 1 pessoa")
    private Integer capacidade;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal preco;
}
