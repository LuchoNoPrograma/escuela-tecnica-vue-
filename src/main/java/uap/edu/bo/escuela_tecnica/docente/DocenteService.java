package uap.edu.bo.escuela_tecnica.docente;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModulo;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModuloRepository;
import uap.edu.bo.escuela_tecnica.persona.PersonaRepository;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografia;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografiaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class DocenteService {

    private final DocenteRepository docenteRepository;
    private final PersonaRepository personaRepository;
    private final DocenteMapper docenteMapper;
    private final CronogramaModuloRepository cronogramaModuloRepository;
    private final RevisionMonografiaRepository revisionMonografiaRepository;

    public DocenteService(final DocenteRepository docenteRepository,
            final PersonaRepository personaRepository, final DocenteMapper docenteMapper,
            final CronogramaModuloRepository cronogramaModuloRepository,
            final RevisionMonografiaRepository revisionMonografiaRepository) {
        this.docenteRepository = docenteRepository;
        this.personaRepository = personaRepository;
        this.docenteMapper = docenteMapper;
        this.cronogramaModuloRepository = cronogramaModuloRepository;
        this.revisionMonografiaRepository = revisionMonografiaRepository;
    }

    public List<DocenteDTO> findAll() {
        final List<Docente> docentes = docenteRepository.findAll(Sort.by("idDocente"));
        return docentes.stream()
                .map(docente -> docenteMapper.updateDocenteDTO(docente, new DocenteDTO()))
                .toList();
    }

    public DocenteDTO get(final Long idDocente) {
        return docenteRepository.findById(idDocente)
                .map(docente -> docenteMapper.updateDocenteDTO(docente, new DocenteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DocenteDTO docenteDTO) {
        final Docente docente = new Docente();
        docenteMapper.updateDocente(docenteDTO, docente, personaRepository);
        return docenteRepository.save(docente).getIdDocente();
    }

    public void update(final Long idDocente, final DocenteDTO docenteDTO) {
        final Docente docente = docenteRepository.findById(idDocente)
                .orElseThrow(NotFoundException::new);
        docenteMapper.updateDocente(docenteDTO, docente, personaRepository);
        docenteRepository.save(docente);
    }

    public void delete(final Long idDocente) {
        docenteRepository.deleteById(idDocente);
    }

    public ReferencedWarning getReferencedWarning(final Long idDocente) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Docente docente = docenteRepository.findById(idDocente)
                .orElseThrow(NotFoundException::new);
        final CronogramaModulo docenteCronogramaModulo = cronogramaModuloRepository.findFirstByDocente(docente);
        if (docenteCronogramaModulo != null) {
            referencedWarning.setKey("docente.cronogramaModulo.docente.referenced");
            referencedWarning.addParam(docenteCronogramaModulo.getIdCronogramaMod());
            return referencedWarning;
        }
        final RevisionMonografia docenteRevisionMonografia = revisionMonografiaRepository.findFirstByDocente(docente);
        if (docenteRevisionMonografia != null) {
            referencedWarning.setKey("docente.revisionMonografia.docente.referenced");
            referencedWarning.addParam(docenteRevisionMonografia.getIdRevisionMonografia());
            return referencedWarning;
        }
        return null;
    }

}
