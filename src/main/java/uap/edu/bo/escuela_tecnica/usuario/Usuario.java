package uap.edu.bo.escuela_tecnica.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.ocupa.Ocupa;
import uap.edu.bo.escuela_tecnica.persona.Persona;
import uap.edu.bo.escuela_tecnica.tarea.Tarea;


@Entity
@Getter
@Setter
public class Usuario extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(length = 50)
    private String nombreUsuario;

    @Column
    private String contrasenaHash;

    @Column(length = 35)
    private String estUsuario;

    @OneToMany(mappedBy = "usuario")
    private Set<Ocupa> listaOcupa;

    @ManyToMany
    @JoinTable(
            name = "asigna",
            joinColumns = @JoinColumn(name = "fk_id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "fk_id_tarea")
    )
    private Set<Tarea> listaTareasAsignadas;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_persona", nullable = false, unique = true)
    private Persona persona;

}
