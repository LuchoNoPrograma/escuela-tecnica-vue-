package uap.edu.bo.escuela_tecnica.preinscripcion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.plan_estudio.PlanEstudio;
import uap.edu.bo.escuela_tecnica.programa.Programa;


@Entity
@Getter
@Setter
public class Preinscripcion extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPreinscripcion;

    @Column(nullable = false, length = 55)
    private String nombres;

    @Column(nullable = false, length = 35)
    private String paterno;

    @Column(length = 35)
    private String materno;

    @Column
    private String ci;

    @Column(length = 15)
    private String celular;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_programa", nullable = false)
    private Programa programa;
}
