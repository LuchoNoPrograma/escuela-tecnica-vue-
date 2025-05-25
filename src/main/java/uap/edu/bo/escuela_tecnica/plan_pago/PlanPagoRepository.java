package uap.edu.bo.escuela_tecnica.plan_pago;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;


public interface PlanPagoRepository extends JpaRepository<PlanPago, Long> {

    PlanPago findFirstByMatricula(Matricula matricula);

}
