package uap.edu.bo.escuela_tecnica.rol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.tarea.Tarea;


@Entity
@Getter
@Setter
public class Rol extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 35)
    private String estCargo;

    @Column(columnDefinition = "text")
    private String descripcionCargo;

    @OneToMany(mappedBy = "rol")
    private Set<Tarea> listaTareas;

}
