package uap.edu.bo.escuela_tecnica.programacion;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModulo;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;


public interface ProgramacionRepository extends JpaRepository<Programacion, Long> {

    Programacion findFirstByCronogramaModulo(CronogramaModulo cronogramaModulo);

    Programacion findFirstByMatricula(Matricula matricula);

}
