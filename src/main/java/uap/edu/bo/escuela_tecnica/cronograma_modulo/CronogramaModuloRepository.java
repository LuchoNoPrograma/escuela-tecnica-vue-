package uap.edu.bo.escuela_tecnica.cronograma_modulo;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.docente.Docente;
import uap.edu.bo.escuela_tecnica.grupo.Grupo;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalle;


public interface CronogramaModuloRepository extends JpaRepository<CronogramaModulo, Long> {

    CronogramaModulo findFirstByPlanEstudioDetalle(PlanEstudioDetalle planEstudioDetalle);

    CronogramaModulo findFirstByGrupo(Grupo grupo);

    CronogramaModulo findFirstByDocente(Docente docente);

}
