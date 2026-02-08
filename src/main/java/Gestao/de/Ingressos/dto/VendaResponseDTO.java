package Gestao.de.Ingressos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaResponseDTO {
    private Long vendaId;
    private String nomeEvento;
    private LocalDateTime dataVenda;
    private BigDecimal valorTotal;
    private List<String> codigosIngressos;
}
