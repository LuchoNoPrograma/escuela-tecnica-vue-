package uap.edu.bo.escuela_tecnica.matricula;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.grupo.Grupo;
import uap.edu.bo.escuela_tecnica.persona.Persona;


public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    Matricula findFirstByGrupo(Grupo grupo);

    Matricula findFirstByPersona(Persona persona);

}
