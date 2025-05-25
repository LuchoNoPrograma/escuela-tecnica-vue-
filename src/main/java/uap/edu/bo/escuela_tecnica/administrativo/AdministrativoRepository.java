package uap.edu.bo.escuela_tecnica.administrativo;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.persona.Persona;


public interface AdministrativoRepository extends JpaRepository<Administrativo, Long> {

    Administrativo findFirstByPersona(Persona persona);

}
