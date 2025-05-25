package uap.edu.bo.escuela_tecnica.docente;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.persona.Persona;


public interface DocenteRepository extends JpaRepository<Docente, Long> {

    Docente findFirstByPersona(Persona persona);

}
