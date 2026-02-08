package Gestao.de.Ingressos.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SqlResultSetMapping(
        name = "IngressoDetalheDTOMapping",
        classes = @ConstructorResult(
                targetClass = IngressoDetalheDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "codigoValidacao", type = String.class),
                        @ColumnResult(name = "nomeEvento", type = String.class),
                        @ColumnResult(name = "dataCompra", type = LocalDateTime.class),
                        @ColumnResult(name = "preco", type = BigDecimal.class),
                        @ColumnResult(name = "status", type = String.class),
                        @ColumnResult(name = "nomeComprador", type = String.class)
                }
        )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IngressoDetalheDTO {
    
    @Id
    private Long id;
    private String codigoValidacao;
    private String nomeEvento;
    private LocalDateTime dataCompra;
    private BigDecimal preco;
    private String status;
    private String nomeComprador;
}
