package uap.edu.bo.escuela_tecnica.calificacion;

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
import uap.edu.bo.escuela_tecnica.criterio_eval.CriterioEval;
import uap.edu.bo.escuela_tecnica.programacion.Programacion;


@Entity
@Getter
@Setter
public class Calificacion extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCalificacion;

    @Column(precision = 7, scale = 2)
    private BigDecimal nota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_criterio_eval", nullable = false)
    private CriterioEval criterioEval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_programacion", nullable = false)
    private Programacion programacion;

}
