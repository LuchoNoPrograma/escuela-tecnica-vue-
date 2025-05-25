package uap.edu.bo.escuela_tecnica.revision_monografia;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.administrativo.AdministrativoRepository;
import uap.edu.bo.escuela_tecnica.docente.DocenteRepository;
import uap.edu.bo.escuela_tecnica.monografia.MonografiaRepository;
import uap.edu.bo.escuela_tecnica.observacion_monografia.ObservacionMonografia;
import uap.edu.bo.escuela_tecnica.observacion_monografia.ObservacionMonografiaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class RevisionMonografiaService {

    private final RevisionMonografiaRepository revisionMonografiaRepository;
    private final MonografiaRepository monografiaRepository;
    private final AdministrativoRepository administrativoRepository;
    private final DocenteRepository docenteRepository;
    private final RevisionMonografiaMapper revisionMonografiaMapper;
    private final ObservacionMonografiaRepository observacionMonografiaRepository;

    public RevisionMonografiaService(
            final RevisionMonografiaRepository revisionMonografiaRepository,
            final MonografiaRepository monografiaRepository,
            final AdministrativoRepository administrativoRepository,
            final DocenteRepository docenteRepository,
            final RevisionMonografiaMapper revisionMonografiaMapper,
            final ObservacionMonografiaRepository observacionMonografiaRepository) {
        this.revisionMonografiaRepository = revisionMonografiaRepository;
        this.monografiaRepository = monografiaRepository;
        this.administrativoRepository = administrativoRepository;
        this.docenteRepository = docenteRepository;
        this.revisionMonografiaMapper = revisionMonografiaMapper;
        this.observacionMonografiaRepository = observacionMonografiaRepository;
    }

    public List<RevisionMonografiaDTO> findAll() {
        final List<RevisionMonografia> revisionMonografias = revisionMonografiaRepository.findAll(Sort.by("idRevisionMonografia"));
        return revisionMonografias.stream()
                .map(revisionMonografia -> revisionMonografiaMapper.updateRevisionMonografiaDTO(revisionMonografia, new RevisionMonografiaDTO()))
                .toList();
    }

    public RevisionMonografiaDTO get(final Long idRevisionMonografia) {
        return revisionMonografiaRepository.findById(idRevisionMonografia)
                .map(revisionMonografia -> revisionMonografiaMapper.updateRevisionMonografiaDTO(revisionMonografia, new RevisionMonografiaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RevisionMonografiaDTO revisionMonografiaDTO) {
        final RevisionMonografia revisionMonografia = new RevisionMonografia();
        revisionMonografiaMapper.updateRevisionMonografia(revisionMonografiaDTO, revisionMonografia, monografiaRepository, administrativoRepository, docenteRepository);
        return revisionMonografiaRepository.save(revisionMonografia).getIdRevisionMonografia();
    }

    public void update(final Long idRevisionMonografia,
            final RevisionMonografiaDTO revisionMonografiaDTO) {
        final RevisionMonografia revisionMonografia = revisionMonografiaRepository.findById(idRevisionMonografia)
                .orElseThrow(NotFoundException::new);
        revisionMonografiaMapper.updateRevisionMonografia(revisionMonografiaDTO, revisionMonografia, monografiaRepository, administrativoRepository, docenteRepository);
        revisionMonografiaRepository.save(revisionMonografia);
    }

    public void delete(final Long idRevisionMonografia) {
        revisionMonografiaRepository.deleteById(idRevisionMonografia);
    }

    public ReferencedWarning getReferencedWarning(final Long idRevisionMonografia) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final RevisionMonografia revisionMonografia = revisionMonografiaRepository.findById(idRevisionMonografia)
                .orElseThrow(NotFoundException::new);
        final ObservacionMonografia revisionMonografiaObservacionMonografia = observacionMonografiaRepository.findFirstByRevisionMonografia(revisionMonografia);
        if (revisionMonografiaObservacionMonografia != null) {
            referencedWarning.setKey("revisionMonografia.observacionMonografia.revisionMonografia.referenced");
            referencedWarning.addParam(revisionMonografiaObservacionMonografia.getIdObservacionMonografia());
            return referencedWarning;
        }
        return null;
    }

}
