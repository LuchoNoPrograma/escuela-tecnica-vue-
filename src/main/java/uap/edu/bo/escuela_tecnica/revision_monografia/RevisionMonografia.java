package uap.edu.bo.escuela_tecnica.revision_monografia;

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
import uap.edu.bo.escuela_tecnica.administrativo.Administrativo;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.docente.Docente;
import uap.edu.bo.escuela_tecnica.monografia.Monografia;
import uap.edu.bo.escuela_tecnica.observacion_monografia.ObservacionMonografia;


@Entity
@Getter
@Setter
public class RevisionMonografia extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRevisionMonografia;

    @Column(nullable = false)
    private LocalDateTime fecHoraDesignacion;

    @Column
    private Boolean esAprobadorFinal;

    @Column
    private LocalDateTime fecHoraRevision;

    @Column(nullable = false, length = 35)
    private String estRevisionMoografia;

    @OneToMany(mappedBy = "revisionMonografia")
    private Set<ObservacionMonografia> listaObservaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_monografia", nullable = false)
    private Monografia monografia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_administrativo")
    private Administrativo administrativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_docente")
    private Docente docente;

}
