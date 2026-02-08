package Gestao.de.Ingressos.model;

import Gestao.de.Ingressos.enums.StatusIngresso;
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
@Table(name = "TBL_INGRESSO")
public class IngressoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INGRESSO")
    @SequenceGenerator(name = "SEQ_INGRESSO", sequenceName = "SQ_INGRESSO", allocationSize = 1)
    @Column(name = "COD_INGRESSO")
    private Long id;

    @Column(name = "COD_VALIDACAO", nullable = false, unique = true)
    private String codigo;

    @Column(name = "DATA_COMPRA", nullable = false)
    private LocalDateTime dataCompra;

    @Column(name = "NOME_LOTE", nullable = false)
    private String lote;

    @Column(name = "VAL_PRECO_PAGO", nullable = false)
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_INGRESSO", nullable = false)
    private StatusIngresso status;

    @ManyToOne
    @JoinColumn(name = "COD_EVENTO", nullable = false)
    private EventoEntity evento;

    @ManyToOne
    @JoinColumn(name = "COD_COMPRADOR", nullable = false)
    private UsuarioEntity USUARIO;

    @ManyToOne
    @JoinColumn(name = "COD_VENDA")
    private VendaEntity venda;
}
