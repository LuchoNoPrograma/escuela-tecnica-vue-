package uap.edu.bo.escuela_tecnica.criterio_eval;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModulo;


public interface CriterioEvalRepository extends JpaRepository<CriterioEval, Long> {

    CriterioEval findFirstByCronogramaMod(CronogramaModulo cronogramaModulo);

}
