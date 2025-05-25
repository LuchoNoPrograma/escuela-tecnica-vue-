package uap.edu.bo.escuela_tecnica.titulacion;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;


public interface TitulacionRepository extends JpaRepository<Titulacion, Long> {

    Titulacion findFirstByMatricula(Matricula matricula);

    boolean existsByMatriculaCodMatricula(Long codMatricula);

}
