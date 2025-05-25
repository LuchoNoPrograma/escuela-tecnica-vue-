package uap.edu.bo.escuela_tecnica.observacion_monografia;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografiaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Service
public class ObservacionMonografiaService {

    private final ObservacionMonografiaRepository observacionMonografiaRepository;
    private final RevisionMonografiaRepository revisionMonografiaRepository;
    private final ObservacionMonografiaMapper observacionMonografiaMapper;

    public ObservacionMonografiaService(
            final ObservacionMonografiaRepository observacionMonografiaRepository,
            final RevisionMonografiaRepository revisionMonografiaRepository,
            final ObservacionMonografiaMapper observacionMonografiaMapper) {
        this.observacionMonografiaRepository = observacionMonografiaRepository;
        this.revisionMonografiaRepository = revisionMonografiaRepository;
        this.observacionMonografiaMapper = observacionMonografiaMapper;
    }

    public List<ObservacionMonografiaDTO> findAll() {
        final List<ObservacionMonografia> observacionMonografias = observacionMonografiaRepository.findAll(Sort.by("idObservacionMonografia"));
        return observacionMonografias.stream()
                .map(observacionMonografia -> observacionMonografiaMapper.updateObservacionMonografiaDTO(observacionMonografia, new ObservacionMonografiaDTO()))
                .toList();
    }

    public ObservacionMonografiaDTO get(final Long idObservacionMonografia) {
        return observacionMonografiaRepository.findById(idObservacionMonografia)
                .map(observacionMonografia -> observacionMonografiaMapper.updateObservacionMonografiaDTO(observacionMonografia, new ObservacionMonografiaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ObservacionMonografiaDTO observacionMonografiaDTO) {
        final ObservacionMonografia observacionMonografia = new ObservacionMonografia();
        observacionMonografiaMapper.updateObservacionMonografia(observacionMonografiaDTO, observacionMonografia, revisionMonografiaRepository);
        return observacionMonografiaRepository.save(observacionMonografia).getIdObservacionMonografia();
    }

    public void update(final Long idObservacionMonografia,
            final ObservacionMonografiaDTO observacionMonografiaDTO) {
        final ObservacionMonografia observacionMonografia = observacionMonografiaRepository.findById(idObservacionMonografia)
                .orElseThrow(NotFoundException::new);
        observacionMonografiaMapper.updateObservacionMonografia(observacionMonografiaDTO, observacionMonografia, revisionMonografiaRepository);
        observacionMonografiaRepository.save(observacionMonografia);
    }

    public void delete(final Long idObservacionMonografia) {
        observacionMonografiaRepository.deleteById(idObservacionMonografia);
    }

}
