package uap.edu.bo.escuela_tecnica.plan_estudio;

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
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalle;
import uap.edu.bo.escuela_tecnica.programa.Programa;


@Entity
@Getter
@Setter
public class PlanEstudio extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlanEstudio;

    @Column(nullable = false)
    private Integer anho;

    @Column(nullable = false)
    private Boolean vigente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_programa", nullable = false)
    private Programa programa;

    @OneToMany(mappedBy = "planEstudio")
    private Set<PlanEstudioDetalle> listaPlanDetalles;

}
