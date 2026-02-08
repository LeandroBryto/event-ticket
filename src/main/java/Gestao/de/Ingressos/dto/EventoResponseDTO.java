package Gestao.de.Ingressos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime dataEvento;
    private String local;
    private Integer capacidade;
    private Integer ingressosDisponiveis;
    private BigDecimal preco;
    private String nomeOrganizador;
}
