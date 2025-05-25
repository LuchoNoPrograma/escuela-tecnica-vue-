package uap.edu.bo.escuela_tecnica.version;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.programa.Programa;


public interface VersionRepository extends JpaRepository<Version, Integer> {

    Version findFirstByPrograma(Programa programa);

}
