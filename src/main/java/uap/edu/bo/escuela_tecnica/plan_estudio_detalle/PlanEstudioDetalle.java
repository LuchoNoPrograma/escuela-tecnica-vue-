package uap.edu.bo.escuela_tecnica.plan_estudio_detalle;

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
import uap.edu.bo.escuela_tecnica.modulo.Modulo;
import uap.edu.bo.escuela_tecnica.nivel.Nivel;
import uap.edu.bo.escuela_tecnica.plan_estudio.PlanEstudio;


@Entity
@Getter
@Setter
public class PlanEstudioDetalle extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlanEstudioDetalle;

    @Column(nullable = false)
    private Integer cargaHoraria;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal creditos;

    @Column(nullable = false)
    private Integer orden;

    @Column(nullable = false, length = 10)
    private String sigla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_nivel", nullable = false)
    private Nivel nivel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_modulo", nullable = false)
    private Modulo modulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_plan_estudio", nullable = false)
    private PlanEstudio planEstudio;

}
