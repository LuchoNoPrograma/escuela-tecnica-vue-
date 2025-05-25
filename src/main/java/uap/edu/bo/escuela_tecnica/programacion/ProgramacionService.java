package uap.edu.bo.escuela_tecnica.programacion;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.calificacion.Calificacion;
import uap.edu.bo.escuela_tecnica.calificacion.CalificacionRepository;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModuloRepository;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class ProgramacionService {

    private final ProgramacionRepository programacionRepository;
    private final CronogramaModuloRepository cronogramaModuloRepository;
    private final MatriculaRepository matriculaRepository;
    private final ProgramacionMapper programacionMapper;
    private final CalificacionRepository calificacionRepository;

    public ProgramacionService(final ProgramacionRepository programacionRepository,
            final CronogramaModuloRepository cronogramaModuloRepository,
            final MatriculaRepository matriculaRepository,
            final ProgramacionMapper programacionMapper,
            final CalificacionRepository calificacionRepository) {
        this.programacionRepository = programacionRepository;
        this.cronogramaModuloRepository = cronogramaModuloRepository;
        this.matriculaRepository = matriculaRepository;
        this.programacionMapper = programacionMapper;
        this.calificacionRepository = calificacionRepository;
    }

    public List<ProgramacionDTO> findAll() {
        final List<Programacion> programacions = programacionRepository.findAll(Sort.by("idProgramacion"));
        return programacions.stream()
                .map(programacion -> programacionMapper.updateProgramacionDTO(programacion, new ProgramacionDTO()))
                .toList();
    }

    public ProgramacionDTO get(final Long idProgramacion) {
        return programacionRepository.findById(idProgramacion)
                .map(programacion -> programacionMapper.updateProgramacionDTO(programacion, new ProgramacionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProgramacionDTO programacionDTO) {
        final Programacion programacion = new Programacion();
        programacionMapper.updateProgramacion(programacionDTO, programacion, cronogramaModuloRepository, matriculaRepository);
        return programacionRepository.save(programacion).getIdProgramacion();
    }

    public void update(final Long idProgramacion, final ProgramacionDTO programacionDTO) {
        final Programacion programacion = programacionRepository.findById(idProgramacion)
                .orElseThrow(NotFoundException::new);
        programacionMapper.updateProgramacion(programacionDTO, programacion, cronogramaModuloRepository, matriculaRepository);
        programacionRepository.save(programacion);
    }

    public void delete(final Long idProgramacion) {
        programacionRepository.deleteById(idProgramacion);
    }

    public ReferencedWarning getReferencedWarning(final Long idProgramacion) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Programacion programacion = programacionRepository.findById(idProgramacion)
                .orElseThrow(NotFoundException::new);
        final Calificacion programacionCalificacion = calificacionRepository.findFirstByProgramacion(programacion);
        if (programacionCalificacion != null) {
            referencedWarning.setKey("programacion.calificacion.programacion.referenced");
            referencedWarning.addParam(programacionCalificacion.getIdCalificacion());
            return referencedWarning;
        }
        return null;
    }

}
