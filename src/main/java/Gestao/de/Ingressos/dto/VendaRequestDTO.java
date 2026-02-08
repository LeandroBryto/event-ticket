package Gestao.de.Ingressos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaRequestDTO {

    @NotNull(message = "O ID do evento é obrigatório")
    private Long eventoId;

    @NotNull(message = "A quantidade de ingressos é obrigatória")
    @Min(value = 1, message = "Você deve comprar pelo menos 1 ingresso")
    private Integer quantidade;
}
