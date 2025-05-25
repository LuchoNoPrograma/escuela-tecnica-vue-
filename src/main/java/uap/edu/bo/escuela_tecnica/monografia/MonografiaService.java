package uap.edu.bo.escuela_tecnica.monografia;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografia;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografiaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class MonografiaService {

    private final MonografiaRepository monografiaRepository;
    private final MatriculaRepository matriculaRepository;
    private final MonografiaMapper monografiaMapper;
    private final RevisionMonografiaRepository revisionMonografiaRepository;

    public MonografiaService(final MonografiaRepository monografiaRepository,
            final MatriculaRepository matriculaRepository, final MonografiaMapper monografiaMapper,
            final RevisionMonografiaRepository revisionMonografiaRepository) {
        this.monografiaRepository = monografiaRepository;
        this.matriculaRepository = matriculaRepository;
        this.monografiaMapper = monografiaMapper;
        this.revisionMonografiaRepository = revisionMonografiaRepository;
    }

    public List<MonografiaDTO> findAll() {
        final List<Monografia> monografias = monografiaRepository.findAll(Sort.by("idMonografia"));
        return monografias.stream()
                .map(monografia -> monografiaMapper.updateMonografiaDTO(monografia, new MonografiaDTO()))
                .toList();
    }

    public MonografiaDTO get(final Long idMonografia) {
        return monografiaRepository.findById(idMonografia)
                .map(monografia -> monografiaMapper.updateMonografiaDTO(monografia, new MonografiaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MonografiaDTO monografiaDTO) {
        final Monografia monografia = new Monografia();
        monografiaMapper.updateMonografia(monografiaDTO, monografia, matriculaRepository);
        return monografiaRepository.save(monografia).getIdMonografia();
    }

    public void update(final Long idMonografia, final MonografiaDTO monografiaDTO) {
        final Monografia monografia = monografiaRepository.findById(idMonografia)
                .orElseThrow(NotFoundException::new);
        monografiaMapper.updateMonografia(monografiaDTO, monografia, matriculaRepository);
        monografiaRepository.save(monografia);
    }

    public void delete(final Long idMonografia) {
        monografiaRepository.deleteById(idMonografia);
    }

    public ReferencedWarning getReferencedWarning(final Long idMonografia) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Monografia monografia = monografiaRepository.findById(idMonografia)
                .orElseThrow(NotFoundException::new);
        final RevisionMonografia monografiaRevisionMonografia = revisionMonografiaRepository.findFirstByMonografia(monografia);
        if (monografiaRevisionMonografia != null) {
            referencedWarning.setKey("monografia.revisionMonografia.monografia.referenced");
            referencedWarning.addParam(monografiaRevisionMonografia.getIdRevisionMonografia());
            return referencedWarning;
        }
        return null;
    }

}
