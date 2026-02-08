package Gestao.de.Ingressos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TBL_VENDA")
public class VendaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VENDA")
    @SequenceGenerator(name = "SEQ_VENDA", sequenceName = "SQ_VENDA", allocationSize = 1)
    @Column(name = "COD_VENDA")
    private Long id;

    @Column(name = "DATA_VENDA", nullable = false)
    private LocalDateTime dataVenda;

    @Column(name = "VAL_TOTAL", nullable = false)
    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "COD_COMPRADOR", nullable = false)
    private UsuarioEntity comprador;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<IngressoEntity> ingressos;
}
