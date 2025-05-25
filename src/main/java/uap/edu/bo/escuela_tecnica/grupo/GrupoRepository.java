package uap.edu.bo.escuela_tecnica.grupo;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.programa.Programa;


public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    Grupo findFirstByPrograma(Programa programa);

}
