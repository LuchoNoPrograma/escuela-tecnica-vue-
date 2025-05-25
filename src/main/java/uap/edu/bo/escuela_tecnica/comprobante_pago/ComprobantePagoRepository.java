package uap.edu.bo.escuela_tecnica.comprobante_pago;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.detalle_pago.DetallePago;


public interface ComprobantePagoRepository extends JpaRepository<ComprobantePago, Long> {

    ComprobantePago findFirstByListaDetallesPagos(DetallePago detallePago);

    List<ComprobantePago> findAllByListaDetallesPagos(DetallePago detallePago);

}
