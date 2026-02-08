package Gestao.de.Ingressos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TBL_EVENTO")
public class EventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVENTO")
    @SequenceGenerator(name = "SEQ_EVENTO", sequenceName = "SQ_EVENTO", allocationSize = 1)
    @Column(name = "COD_EVENTO")
    private Long id;

    @Column(name = "NOME_EVENTO", nullable = false)
    private String nome;

    @Column(name = "TXT_DESCRICAO")
    private String descricao;

    @Column(name = "DATA_EVENTO", nullable = false)
    private LocalDateTime dataEvento;

    @Column(name = "LOCAL_EVENTO", nullable = false)
    private String local;

    @Column(name = "QTD_CAPACIDADE", nullable = false)
    private Integer capacidade;

    @Column(name = "QTD_DISPONIVEL")
    private Integer ingressosDisponiveis;

    @Column(name = "VAL_PRECO_INGRESSO", nullable = false)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "COD_ORGANIZADOR", nullable = false)
    private UsuarioEntity organizador;
}
