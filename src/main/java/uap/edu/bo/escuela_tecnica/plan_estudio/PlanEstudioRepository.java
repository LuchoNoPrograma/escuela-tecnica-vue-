package uap.edu.bo.escuela_tecnica.plan_estudio;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.programa.Programa;


public interface PlanEstudioRepository extends JpaRepository<PlanEstudio, Long> {

    PlanEstudio findFirstByPrograma(Programa programa);

}
