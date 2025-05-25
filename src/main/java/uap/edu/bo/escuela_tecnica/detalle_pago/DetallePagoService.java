package uap.edu.bo.escuela_tecnica.detalle_pago;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.comprobante_pago.ComprobantePagoRepository;
import uap.edu.bo.escuela_tecnica.plan_pago.PlanPagoRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Service
@Transactional
public class DetallePagoService {

    private final DetallePagoRepository detallePagoRepository;
    private final PlanPagoRepository planPagoRepository;
    private final DetallePagoMapper detallePagoMapper;
    private final ComprobantePagoRepository comprobantePagoRepository;

    public DetallePagoService(final DetallePagoRepository detallePagoRepository,
            final PlanPagoRepository planPagoRepository, final DetallePagoMapper detallePagoMapper,
            final ComprobantePagoRepository comprobantePagoRepository) {
        this.detallePagoRepository = detallePagoRepository;
        this.planPagoRepository = planPagoRepository;
        this.detallePagoMapper = detallePagoMapper;
        this.comprobantePagoRepository = comprobantePagoRepository;
    }

    public List<DetallePagoDTO> findAll() {
        final List<DetallePago> detallePagoes = detallePagoRepository.findAll(Sort.by("idDetallePago"));
        return detallePagoes.stream()
                .map(detallePago -> detallePagoMapper.updateDetallePagoDTO(detallePago, new DetallePagoDTO()))
                .toList();
    }

    public DetallePagoDTO get(final Long idDetallePago) {
        return detallePagoRepository.findById(idDetallePago)
                .map(detallePago -> detallePagoMapper.updateDetallePagoDTO(detallePago, new DetallePagoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DetallePagoDTO detallePagoDTO) {
        final DetallePago detallePago = new DetallePago();
        detallePagoMapper.updateDetallePago(detallePagoDTO, detallePago, planPagoRepository);
        return detallePagoRepository.save(detallePago).getIdDetallePago();
    }

    public void update(final Long idDetallePago, final DetallePagoDTO detallePagoDTO) {
        final DetallePago detallePago = detallePagoRepository.findById(idDetallePago)
                .orElseThrow(NotFoundException::new);
        detallePagoMapper.updateDetallePago(detallePagoDTO, detallePago, planPagoRepository);
        detallePagoRepository.save(detallePago);
    }

    public void delete(final Long idDetallePago) {
        final DetallePago detallePago = detallePagoRepository.findById(idDetallePago)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        comprobantePagoRepository.findAllByListaDetallesPagos(detallePago)
                .forEach(comprobantePago -> comprobantePago.getListaDetallesPagos().remove(detallePago));
        detallePagoRepository.delete(detallePago);
    }

}
