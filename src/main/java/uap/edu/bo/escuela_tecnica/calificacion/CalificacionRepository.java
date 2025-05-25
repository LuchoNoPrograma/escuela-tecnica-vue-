package uap.edu.bo.escuela_tecnica.calificacion;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.criterio_eval.CriterioEval;
import uap.edu.bo.escuela_tecnica.programacion.Programacion;


public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    Calificacion findFirstByCriterioEval(CriterioEval criterioEval);

    Calificacion findFirstByProgramacion(Programacion programacion);

}
