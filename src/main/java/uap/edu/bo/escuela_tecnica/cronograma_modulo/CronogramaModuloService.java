package uap.edu.bo.escuela_tecnica.cronograma_modulo;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.criterio_eval.CriterioEval;
import uap.edu.bo.escuela_tecnica.criterio_eval.CriterioEvalRepository;
import uap.edu.bo.escuela_tecnica.docente.DocenteRepository;
import uap.edu.bo.escuela_tecnica.grupo.GrupoRepository;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalleRepository;
import uap.edu.bo.escuela_tecnica.programacion.Programacion;
import uap.edu.bo.escuela_tecnica.programacion.ProgramacionRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class CronogramaModuloService {

    private final CronogramaModuloRepository cronogramaModuloRepository;
    private final PlanEstudioDetalleRepository planEstudioDetalleRepository;
    private final GrupoRepository grupoRepository;
    private final DocenteRepository docenteRepository;
    private final CronogramaModuloMapper cronogramaModuloMapper;
    private final CriterioEvalRepository criterioEvalRepository;
    private final ProgramacionRepository programacionRepository;

    public CronogramaModuloService(final CronogramaModuloRepository cronogramaModuloRepository,
            final PlanEstudioDetalleRepository planEstudioDetalleRepository,
            final GrupoRepository grupoRepository, final DocenteRepository docenteRepository,
            final CronogramaModuloMapper cronogramaModuloMapper,
            final CriterioEvalRepository criterioEvalRepository,
            final ProgramacionRepository programacionRepository) {
        this.cronogramaModuloRepository = cronogramaModuloRepository;
        this.planEstudioDetalleRepository = planEstudioDetalleRepository;
        this.grupoRepository = grupoRepository;
        this.docenteRepository = docenteRepository;
        this.cronogramaModuloMapper = cronogramaModuloMapper;
        this.criterioEvalRepository = criterioEvalRepository;
        this.programacionRepository = programacionRepository;
    }

    public List<CronogramaModuloDTO> findAll() {
        final List<CronogramaModulo> cronogramaModuloes = cronogramaModuloRepository.findAll(Sort.by("idCronogramaMod"));
        return cronogramaModuloes.stream()
                .map(cronogramaModulo -> cronogramaModuloMapper.updateCronogramaModuloDTO(cronogramaModulo, new CronogramaModuloDTO()))
                .toList();
    }

    public CronogramaModuloDTO get(final Long idCronogramaMod) {
        return cronogramaModuloRepository.findById(idCronogramaMod)
                .map(cronogramaModulo -> cronogramaModuloMapper.updateCronogramaModuloDTO(cronogramaModulo, new CronogramaModuloDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CronogramaModuloDTO cronogramaModuloDTO) {
        final CronogramaModulo cronogramaModulo = new CronogramaModulo();
        cronogramaModuloMapper.updateCronogramaModulo(cronogramaModuloDTO, cronogramaModulo, planEstudioDetalleRepository, grupoRepository, docenteRepository);
        return cronogramaModuloRepository.save(cronogramaModulo).getIdCronogramaMod();
    }

    public void update(final Long idCronogramaMod, final CronogramaModuloDTO cronogramaModuloDTO) {
        final CronogramaModulo cronogramaModulo = cronogramaModuloRepository.findById(idCronogramaMod)
                .orElseThrow(NotFoundException::new);
        cronogramaModuloMapper.updateCronogramaModulo(cronogramaModuloDTO, cronogramaModulo, planEstudioDetalleRepository, grupoRepository, docenteRepository);
        cronogramaModuloRepository.save(cronogramaModulo);
    }

    public void delete(final Long idCronogramaMod) {
        cronogramaModuloRepository.deleteById(idCronogramaMod);
    }

    public ReferencedWarning getReferencedWarning(final Long idCronogramaMod) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final CronogramaModulo cronogramaModulo = cronogramaModuloRepository.findById(idCronogramaMod)
                .orElseThrow(NotFoundException::new);
        final CriterioEval cronogramaModCriterioEval = criterioEvalRepository.findFirstByCronogramaMod(cronogramaModulo);
        if (cronogramaModCriterioEval != null) {
            referencedWarning.setKey("cronogramaModulo.criterioEval.cronogramaMod.referenced");
            referencedWarning.addParam(cronogramaModCriterioEval.getIdCriterioEval());
            return referencedWarning;
        }
        final Programacion cronogramaModuloProgramacion = programacionRepository.findFirstByCronogramaModulo(cronogramaModulo);
        if (cronogramaModuloProgramacion != null) {
            referencedWarning.setKey("cronogramaModulo.programacion.cronogramaModulo.referenced");
            referencedWarning.addParam(cronogramaModuloProgramacion.getIdProgramacion());
            return referencedWarning;
        }
        return null;
    }

}
