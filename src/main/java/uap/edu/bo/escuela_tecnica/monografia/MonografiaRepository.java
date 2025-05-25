package uap.edu.bo.escuela_tecnica.monografia;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;


public interface MonografiaRepository extends JpaRepository<Monografia, Long> {

    Monografia findFirstByMatricula(Matricula matricula);

}
