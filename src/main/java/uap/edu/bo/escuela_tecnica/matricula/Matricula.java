package uap.edu.bo.escuela_tecnica.matricula;

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
import uap.edu.bo.escuela_tecnica.grupo.Grupo;
import uap.edu.bo.escuela_tecnica.persona.Persona;
import uap.edu.bo.escuela_tecnica.plan_pago.PlanPago;
import uap.edu.bo.escuela_tecnica.programacion.Programacion;


@Entity
@Getter
@Setter
public class Matricula extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codMatricula;

    @Column
    private LocalDate fecMatriculacion;

    @Column(nullable = false, length = 35)
    private String estMatricula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_grupo", nullable = false)
    private Grupo grupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_persona", nullable = false)
    private Persona persona;

    @OneToMany(mappedBy = "matricula")
    private Set<PlanPago> listaPlanPago;

    @OneToMany(mappedBy = "matricula")
    private Set<Programacion> listaProgramaciones;

}
