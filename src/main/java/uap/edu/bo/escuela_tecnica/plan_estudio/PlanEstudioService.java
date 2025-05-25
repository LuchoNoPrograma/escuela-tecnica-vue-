package uap.edu.bo.escuela_tecnica.plan_estudio;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalle;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalleRepository;
import uap.edu.bo.escuela_tecnica.programa.ProgramaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class PlanEstudioService {

    private final PlanEstudioRepository planEstudioRepository;
    private final ProgramaRepository programaRepository;
    private final PlanEstudioMapper planEstudioMapper;
    private final PlanEstudioDetalleRepository planEstudioDetalleRepository;

    public PlanEstudioService(final PlanEstudioRepository planEstudioRepository,
            final ProgramaRepository programaRepository, final PlanEstudioMapper planEstudioMapper,
            final PlanEstudioDetalleRepository planEstudioDetalleRepository) {
        this.planEstudioRepository = planEstudioRepository;
        this.programaRepository = programaRepository;
        this.planEstudioMapper = planEstudioMapper;
        this.planEstudioDetalleRepository = planEstudioDetalleRepository;
    }

    public List<PlanEstudioDTO> findAll() {
        final List<PlanEstudio> planEstudios = planEstudioRepository.findAll(Sort.by("idPlanEstudio"));
        return planEstudios.stream()
                .map(planEstudio -> planEstudioMapper.updatePlanEstudioDTO(planEstudio, new PlanEstudioDTO()))
                .toList();
    }

    public PlanEstudioDTO get(final Long idPlanEstudio) {
        return planEstudioRepository.findById(idPlanEstudio)
                .map(planEstudio -> planEstudioMapper.updatePlanEstudioDTO(planEstudio, new PlanEstudioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PlanEstudioDTO planEstudioDTO) {
        final PlanEstudio planEstudio = new PlanEstudio();
        planEstudioMapper.updatePlanEstudio(planEstudioDTO, planEstudio, programaRepository);
        return planEstudioRepository.save(planEstudio).getIdPlanEstudio();
    }

    public void update(final Long idPlanEstudio, final PlanEstudioDTO planEstudioDTO) {
        final PlanEstudio planEstudio = planEstudioRepository.findById(idPlanEstudio)
                .orElseThrow(NotFoundException::new);
        planEstudioMapper.updatePlanEstudio(planEstudioDTO, planEstudio, programaRepository);
        planEstudioRepository.save(planEstudio);
    }

    public void delete(final Long idPlanEstudio) {
        planEstudioRepository.deleteById(idPlanEstudio);
    }

    public ReferencedWarning getReferencedWarning(final Long idPlanEstudio) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PlanEstudio planEstudio = planEstudioRepository.findById(idPlanEstudio)
                .orElseThrow(NotFoundException::new);
        final PlanEstudioDetalle planEstudioPlanEstudioDetalle = planEstudioDetalleRepository.findFirstByPlanEstudio(planEstudio);
        if (planEstudioPlanEstudioDetalle != null) {
            referencedWarning.setKey("planEstudio.planEstudioDetalle.planEstudio.referenced");
            referencedWarning.addParam(planEstudioPlanEstudioDetalle.getIdPlanEstudioDetalle());
            return referencedWarning;
        }
        return null;
    }

}
