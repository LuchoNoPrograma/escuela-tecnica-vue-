package uap.edu.bo.escuela_tecnica.criterio_eval;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.calificacion.Calificacion;
import uap.edu.bo.escuela_tecnica.calificacion.CalificacionRepository;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModuloRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class CriterioEvalService {

    private final CriterioEvalRepository criterioEvalRepository;
    private final CronogramaModuloRepository cronogramaModuloRepository;
    private final CriterioEvalMapper criterioEvalMapper;
    private final CalificacionRepository calificacionRepository;

    public CriterioEvalService(final CriterioEvalRepository criterioEvalRepository,
            final CronogramaModuloRepository cronogramaModuloRepository,
            final CriterioEvalMapper criterioEvalMapper,
            final CalificacionRepository calificacionRepository) {
        this.criterioEvalRepository = criterioEvalRepository;
        this.cronogramaModuloRepository = cronogramaModuloRepository;
        this.criterioEvalMapper = criterioEvalMapper;
        this.calificacionRepository = calificacionRepository;
    }

    public List<CriterioEvalDTO> findAll() {
        final List<CriterioEval> criterioEvals = criterioEvalRepository.findAll(Sort.by("idCriterioEval"));
        return criterioEvals.stream()
                .map(criterioEval -> criterioEvalMapper.updateCriterioEvalDTO(criterioEval, new CriterioEvalDTO()))
                .toList();
    }

    public CriterioEvalDTO get(final Long idCriterioEval) {
        return criterioEvalRepository.findById(idCriterioEval)
                .map(criterioEval -> criterioEvalMapper.updateCriterioEvalDTO(criterioEval, new CriterioEvalDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CriterioEvalDTO criterioEvalDTO) {
        final CriterioEval criterioEval = new CriterioEval();
        criterioEvalMapper.updateCriterioEval(criterioEvalDTO, criterioEval, cronogramaModuloRepository);
        return criterioEvalRepository.save(criterioEval).getIdCriterioEval();
    }

    public void update(final Long idCriterioEval, final CriterioEvalDTO criterioEvalDTO) {
        final CriterioEval criterioEval = criterioEvalRepository.findById(idCriterioEval)
                .orElseThrow(NotFoundException::new);
        criterioEvalMapper.updateCriterioEval(criterioEvalDTO, criterioEval, cronogramaModuloRepository);
        criterioEvalRepository.save(criterioEval);
    }

    public void delete(final Long idCriterioEval) {
        criterioEvalRepository.deleteById(idCriterioEval);
    }

    public ReferencedWarning getReferencedWarning(final Long idCriterioEval) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final CriterioEval criterioEval = criterioEvalRepository.findById(idCriterioEval)
                .orElseThrow(NotFoundException::new);
        final Calificacion criterioEvalCalificacion = calificacionRepository.findFirstByCriterioEval(criterioEval);
        if (criterioEvalCalificacion != null) {
            referencedWarning.setKey("criterioEval.calificacion.criterioEval.referenced");
            referencedWarning.addParam(criterioEvalCalificacion.getIdCalificacion());
            return referencedWarning;
        }
        return null;
    }

}
