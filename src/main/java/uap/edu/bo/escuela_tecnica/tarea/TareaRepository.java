package uap.edu.bo.escuela_tecnica.tarea;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.rol.Rol;


public interface TareaRepository extends JpaRepository<Tarea, Long> {

    Tarea findTopByNombreTarea(String nombreTarea);

    Tarea findFirstByRol(Rol rol);

}
