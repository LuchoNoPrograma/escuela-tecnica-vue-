package uap.edu.bo.escuela_tecnica.observacion_monografia;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografia;


public interface ObservacionMonografiaRepository extends JpaRepository<ObservacionMonografia, Long> {

    ObservacionMonografia findFirstByRevisionMonografia(RevisionMonografia revisionMonografia);

}
