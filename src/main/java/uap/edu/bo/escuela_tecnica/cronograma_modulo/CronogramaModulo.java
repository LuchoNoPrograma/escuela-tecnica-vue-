package uap.edu.bo.escuela_tecnica.cronograma_modulo;

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
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.docente.Docente;
import uap.edu.bo.escuela_tecnica.grupo.Grupo;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalle;
import uap.edu.bo.escuela_tecnica.programacion.Programacion;


@Entity
@Getter
@Setter
public class CronogramaModulo extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCronogramaMod;

    @Column
    private LocalDate fecInicio;

    @Column
    private LocalDate fecFin;

    @Column(nullable = false, length = 35)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_plan_estudio_detalle", nullable = false)
    private PlanEstudioDetalle planEstudioDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_grupo", nullable = false)
    private Grupo grupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_docente")
    private Docente docente;

    @OneToMany(mappedBy = "cronogramaModulo")
    private Set<Programacion> listaProgramaciones;

}
