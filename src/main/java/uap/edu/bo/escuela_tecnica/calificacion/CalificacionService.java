package uap.edu.bo.escuela_tecnica.calificacion;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.criterio_eval.CriterioEvalRepository;
import uap.edu.bo.escuela_tecnica.programacion.ProgramacionRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Service
public class CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final CriterioEvalRepository criterioEvalRepository;
    private final ProgramacionRepository programacionRepository;
    private final CalificacionMapper calificacionMapper;

    public CalificacionService(final CalificacionRepository calificacionRepository,
            final CriterioEvalRepository criterioEvalRepository,
            final ProgramacionRepository programacionRepository,
            final CalificacionMapper calificacionMapper) {
        this.calificacionRepository = calificacionRepository;
        this.criterioEvalRepository = criterioEvalRepository;
        this.programacionRepository = programacionRepository;
        this.calificacionMapper = calificacionMapper;
    }

    public List<CalificacionDTO> findAll() {
        final List<Calificacion> calificacions = calificacionRepository.findAll(Sort.by("idCalificacion"));
        return calificacions.stream()
                .map(calificacion -> calificacionMapper.updateCalificacionDTO(calificacion, new CalificacionDTO()))
                .toList();
    }

    public CalificacionDTO get(final Long idCalificacion) {
        return calificacionRepository.findById(idCalificacion)
                .map(calificacion -> calificacionMapper.updateCalificacionDTO(calificacion, new CalificacionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CalificacionDTO calificacionDTO) {
        final Calificacion calificacion = new Calificacion();
        calificacionMapper.updateCalificacion(calificacionDTO, calificacion, criterioEvalRepository, programacionRepository);
        return calificacionRepository.save(calificacion).getIdCalificacion();
    }

    public void update(final Long idCalificacion, final CalificacionDTO calificacionDTO) {
        final Calificacion calificacion = calificacionRepository.findById(idCalificacion)
                .orElseThrow(NotFoundException::new);
        calificacionMapper.updateCalificacion(calificacionDTO, calificacion, criterioEvalRepository, programacionRepository);
        calificacionRepository.save(calificacion);
    }

    public void delete(final Long idCalificacion) {
        calificacionRepository.deleteById(idCalificacion);
    }

}
