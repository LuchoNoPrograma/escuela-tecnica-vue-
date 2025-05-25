package uap.edu.bo.escuela_tecnica.tarea;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.rol.Rol;


@Entity
@Getter
@Setter
public class Tarea extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarea;

    @Column(nullable = false, length = 50)
    private String nombreTarea;

    @Column(columnDefinition = "text")
    private String descripTarea;

    @Column(nullable = false)
    private Boolean estTarea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_rol", nullable = false)
    private Rol rol;

}
