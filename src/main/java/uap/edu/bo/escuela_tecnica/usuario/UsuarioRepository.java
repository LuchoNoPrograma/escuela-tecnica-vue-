package uap.edu.bo.escuela_tecnica.usuario;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.persona.Persona;
import uap.edu.bo.escuela_tecnica.tarea.Tarea;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @EntityGraph(attributePaths = "listaTareasAsignadas")
    Usuario findByNombreUsuarioIgnoreCase(String nombreUsuario);

    Usuario findFirstByListaTareasAsignadas(Tarea tarea);

    Usuario findFirstByPersona(Persona persona);

    List<Usuario> findAllByListaTareasAsignadas(Tarea tarea);

    boolean existsByPersonaIdPersona(Long idPersona);

}
