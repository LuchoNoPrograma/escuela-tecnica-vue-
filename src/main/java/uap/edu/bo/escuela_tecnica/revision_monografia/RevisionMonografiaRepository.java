package uap.edu.bo.escuela_tecnica.revision_monografia;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.administrativo.Administrativo;
import uap.edu.bo.escuela_tecnica.docente.Docente;
import uap.edu.bo.escuela_tecnica.monografia.Monografia;


public interface RevisionMonografiaRepository extends JpaRepository<RevisionMonografia, Long> {

    RevisionMonografia findFirstByMonografia(Monografia monografia);

    RevisionMonografia findFirstByAdministrativo(Administrativo administrativo);

    RevisionMonografia findFirstByDocente(Docente docente);

}
