package uap.edu.bo.escuela_tecnica.plan_estudio_detalle;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModulo;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModuloRepository;
import uap.edu.bo.escuela_tecnica.modulo.ModuloRepository;
import uap.edu.bo.escuela_tecnica.nivel.NivelRepository;
import uap.edu.bo.escuela_tecnica.plan_estudio.PlanEstudioRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class PlanEstudioDetalleService {

    private final PlanEstudioDetalleRepository planEstudioDetalleRepository;
    private final NivelRepository nivelRepository;
    private final ModuloRepository moduloRepository;
    private final PlanEstudioRepository planEstudioRepository;
    private final PlanEstudioDetalleMapper planEstudioDetalleMapper;
    private final CronogramaModuloRepository cronogramaModuloRepository;

    public PlanEstudioDetalleService(
            final PlanEstudioDetalleRepository planEstudioDetalleRepository,
            final NivelRepository nivelRepository, final ModuloRepository moduloRepository,
            final PlanEstudioRepository planEstudioRepository,
            final PlanEstudioDetalleMapper planEstudioDetalleMapper,
            final CronogramaModuloRepository cronogramaModuloRepository) {
        this.planEstudioDetalleRepository = planEstudioDetalleRepository;
        this.nivelRepository = nivelRepository;
        this.moduloRepository = moduloRepository;
        this.planEstudioRepository = planEstudioRepository;
        this.planEstudioDetalleMapper = planEstudioDetalleMapper;
        this.cronogramaModuloRepository = cronogramaModuloRepository;
    }

    public List<PlanEstudioDetalleDTO> findAll() {
        final List<PlanEstudioDetalle> planEstudioDetalles = planEstudioDetalleRepository.findAll(Sort.by("idPlanEstudioDetalle"));
        return planEstudioDetalles.stream()
                .map(planEstudioDetalle -> planEstudioDetalleMapper.updatePlanEstudioDetalleDTO(planEstudioDetalle, new PlanEstudioDetalleDTO()))
                .toList();
    }

    public PlanEstudioDetalleDTO get(final Long idPlanEstudioDetalle) {
        return planEstudioDetalleRepository.findById(idPlanEstudioDetalle)
                .map(planEstudioDetalle -> planEstudioDetalleMapper.updatePlanEstudioDetalleDTO(planEstudioDetalle, new PlanEstudioDetalleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PlanEstudioDetalleDTO planEstudioDetalleDTO) {
        final PlanEstudioDetalle planEstudioDetalle = new PlanEstudioDetalle();
        planEstudioDetalleMapper.updatePlanEstudioDetalle(planEstudioDetalleDTO, planEstudioDetalle, nivelRepository, moduloRepository, planEstudioRepository);
        return planEstudioDetalleRepository.save(planEstudioDetalle).getIdPlanEstudioDetalle();
    }

    public void update(final Long idPlanEstudioDetalle,
            final PlanEstudioDetalleDTO planEstudioDetalleDTO) {
        final PlanEstudioDetalle planEstudioDetalle = planEstudioDetalleRepository.findById(idPlanEstudioDetalle)
                .orElseThrow(NotFoundException::new);
        planEstudioDetalleMapper.updatePlanEstudioDetalle(planEstudioDetalleDTO, planEstudioDetalle, nivelRepository, moduloRepository, planEstudioRepository);
        planEstudioDetalleRepository.save(planEstudioDetalle);
    }

    public void delete(final Long idPlanEstudioDetalle) {
        planEstudioDetalleRepository.deleteById(idPlanEstudioDetalle);
    }

    public ReferencedWarning getReferencedWarning(final Long idPlanEstudioDetalle) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PlanEstudioDetalle planEstudioDetalle = planEstudioDetalleRepository.findById(idPlanEstudioDetalle)
                .orElseThrow(NotFoundException::new);
        final CronogramaModulo planEstudioDetalleCronogramaModulo = cronogramaModuloRepository.findFirstByPlanEstudioDetalle(planEstudioDetalle);
        if (planEstudioDetalleCronogramaModulo != null) {
            referencedWarning.setKey("planEstudioDetalle.cronogramaModulo.planEstudioDetalle.referenced");
            referencedWarning.addParam(planEstudioDetalleCronogramaModulo.getIdCronogramaMod());
            return referencedWarning;
        }
        return null;
    }

}
