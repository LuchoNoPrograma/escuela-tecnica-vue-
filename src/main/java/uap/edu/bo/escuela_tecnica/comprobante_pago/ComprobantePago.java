package uap.edu.bo.escuela_tecnica.comprobante_pago;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.detalle_pago.DetallePago;


@Entity
@Getter
@Setter
public class ComprobantePago extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComprobante;

    @Column(nullable = false, length = 25)
    private String codComprobante;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDateTime fecComprobante;

    @Column(nullable = false, length = 35)
    private String tipoDeposito;

    @ManyToMany
    @JoinTable(
            name = "comprobante_detalle_pago",
            joinColumns = @JoinColumn(name = "fk_id_comprobante"),
            inverseJoinColumns = @JoinColumn(name = "fk_id_detalle_pago")
    )
    private Set<DetallePago> listaDetallesPagos;

}
