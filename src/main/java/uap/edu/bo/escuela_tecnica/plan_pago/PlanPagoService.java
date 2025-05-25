package uap.edu.bo.escuela_tecnica.plan_pago;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.detalle_pago.DetallePago;
import uap.edu.bo.escuela_tecnica.detalle_pago.DetallePagoRepository;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class PlanPagoService {

    private final PlanPagoRepository planPagoRepository;
    private final MatriculaRepository matriculaRepository;
    private final PlanPagoMapper planPagoMapper;
    private final DetallePagoRepository detallePagoRepository;

    public PlanPagoService(final PlanPagoRepository planPagoRepository,
            final MatriculaRepository matriculaRepository, final PlanPagoMapper planPagoMapper,
            final DetallePagoRepository detallePagoRepository) {
        this.planPagoRepository = planPagoRepository;
        this.matriculaRepository = matriculaRepository;
        this.planPagoMapper = planPagoMapper;
        this.detallePagoRepository = detallePagoRepository;
    }

    public List<PlanPagoDTO> findAll() {
        final List<PlanPago> planPagoes = planPagoRepository.findAll(Sort.by("idPlanPago"));
        return planPagoes.stream()
                .map(planPago -> planPagoMapper.updatePlanPagoDTO(planPago, new PlanPagoDTO()))
                .toList();
    }

    public PlanPagoDTO get(final Long idPlanPago) {
        return planPagoRepository.findById(idPlanPago)
                .map(planPago -> planPagoMapper.updatePlanPagoDTO(planPago, new PlanPagoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PlanPagoDTO planPagoDTO) {
        final PlanPago planPago = new PlanPago();
        planPagoMapper.updatePlanPago(planPagoDTO, planPago, matriculaRepository);
        return planPagoRepository.save(planPago).getIdPlanPago();
    }

    public void update(final Long idPlanPago, final PlanPagoDTO planPagoDTO) {
        final PlanPago planPago = planPagoRepository.findById(idPlanPago)
                .orElseThrow(NotFoundException::new);
        planPagoMapper.updatePlanPago(planPagoDTO, planPago, matriculaRepository);
        planPagoRepository.save(planPago);
    }

    public void delete(final Long idPlanPago) {
        planPagoRepository.deleteById(idPlanPago);
    }

    public ReferencedWarning getReferencedWarning(final Long idPlanPago) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PlanPago planPago = planPagoRepository.findById(idPlanPago)
                .orElseThrow(NotFoundException::new);
        final DetallePago planPagoDetallePago = detallePagoRepository.findFirstByPlanPago(planPago);
        if (planPagoDetallePago != null) {
            referencedWarning.setKey("planPago.detallePago.planPago.referenced");
            referencedWarning.addParam(planPagoDetallePago.getIdDetallePago());
            return referencedWarning;
        }
        return null;
    }

}
