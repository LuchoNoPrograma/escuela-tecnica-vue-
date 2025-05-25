package uap.edu.bo.escuela_tecnica.criterio_eval;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModulo;


@Entity
@Getter
@Setter
public class CriterioEval extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCriterioEval;

    @Column(nullable = false, length = 25)
    private String nombreCrit;

    @Column(length = 155)
    private String descripcion;

    @Column(nullable = false)
    private Integer ponderacion;

    @Column(nullable = false)
    private Integer orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_cronograma_mod", nullable = false)
    private CronogramaModulo cronogramaMod;

}
