package uap.edu.bo.escuela_tecnica.programacion;

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
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.calificacion.Calificacion;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModulo;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;


@Entity
@Getter
@Setter
public class Programacion extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProgramacion;

    @Column
    private String observacion;

    @Column(nullable = false)
    private LocalDateTime fecProgramacion;

    @Column(nullable = false, length = 35)
    private String estCalificacion;

    @Column(nullable = false, length = 35)
    private String tipoProgramacion;

    @Column
    private Integer calificacionFinal;

    @OneToMany(mappedBy = "programacion")
    private Set<Calificacion> listaCalificaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_cronograma_modulo", nullable = false)
    private CronogramaModulo cronogramaModulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_cod_matricula", nullable = false)
    private Matricula matricula;

}
