package uap.edu.bo.escuela_tecnica.plan_estudio_detalle;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.modulo.Modulo;
import uap.edu.bo.escuela_tecnica.nivel.Nivel;
import uap.edu.bo.escuela_tecnica.plan_estudio.PlanEstudio;


public interface PlanEstudioDetalleRepository extends JpaRepository<PlanEstudioDetalle, Long> {

    PlanEstudioDetalle findFirstByNivel(Nivel nivel);

    PlanEstudioDetalle findFirstByModulo(Modulo modulo);

    PlanEstudioDetalle findFirstByPlanEstudio(PlanEstudio planEstudio);

}
