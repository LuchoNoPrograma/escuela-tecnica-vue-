package uap.edu.bo.escuela_tecnica.detalle_pago;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.plan_pago.PlanPago;


public interface DetallePagoRepository extends JpaRepository<DetallePago, Long> {

    DetallePago findFirstByPlanPago(PlanPago planPago);

}
