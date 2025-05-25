package uap.edu.bo.escuela_tecnica.detalle_pago;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.plan_pago.PlanPago;


@Entity
@Getter
@Setter
public class DetallePago extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallePago;

    @Column(precision = 12, scale = 2)
    private BigDecimal montoPagadoDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_plan_pago", nullable = false)
    private PlanPago planPago;

}
