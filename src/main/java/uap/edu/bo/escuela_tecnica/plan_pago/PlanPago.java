package uap.edu.bo.escuela_tecnica.plan_pago;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.detalle_pago.DetallePago;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;


@Entity
@Getter
@Setter
public class PlanPago extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlanPago;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal deudaTotal;

    @Column(precision = 12, scale = 2)
    private BigDecimal pagadoTotal;

    @Column(nullable = false, length = 35)
    private String estPlanPago;

    @Column(nullable = false, length = 155)
    private String concepto;

    @OneToMany(mappedBy = "planPago")
    private Set<DetallePago> listaDetallesPagos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_cod_matricula", nullable = false)
    private Matricula matricula;

}
