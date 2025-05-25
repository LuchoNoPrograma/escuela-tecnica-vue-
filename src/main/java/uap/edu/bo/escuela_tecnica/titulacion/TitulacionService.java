package uap.edu.bo.escuela_tecnica.titulacion;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Service
public class TitulacionService {

    private final TitulacionRepository titulacionRepository;
    private final MatriculaRepository matriculaRepository;
    private final TitulacionMapper titulacionMapper;

    public TitulacionService(final TitulacionRepository titulacionRepository,
            final MatriculaRepository matriculaRepository,
            final TitulacionMapper titulacionMapper) {
        this.titulacionRepository = titulacionRepository;
        this.matriculaRepository = matriculaRepository;
        this.titulacionMapper = titulacionMapper;
    }

    public List<TitulacionDTO> findAll() {
        final List<Titulacion> titulacions = titulacionRepository.findAll(Sort.by("idTitulacion"));
        return titulacions.stream()
                .map(titulacion -> titulacionMapper.updateTitulacionDTO(titulacion, new TitulacionDTO()))
                .toList();
    }

    public TitulacionDTO get(final Long idTitulacion) {
        return titulacionRepository.findById(idTitulacion)
                .map(titulacion -> titulacionMapper.updateTitulacionDTO(titulacion, new TitulacionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TitulacionDTO titulacionDTO) {
        final Titulacion titulacion = new Titulacion();
        titulacionMapper.updateTitulacion(titulacionDTO, titulacion, matriculaRepository);
        return titulacionRepository.save(titulacion).getIdTitulacion();
    }

    public void update(final Long idTitulacion, final TitulacionDTO titulacionDTO) {
        final Titulacion titulacion = titulacionRepository.findById(idTitulacion)
                .orElseThrow(NotFoundException::new);
        titulacionMapper.updateTitulacion(titulacionDTO, titulacion, matriculaRepository);
        titulacionRepository.save(titulacion);
    }

    public void delete(final Long idTitulacion) {
        titulacionRepository.deleteById(idTitulacion);
    }

    public boolean matriculaExists(final Long codMatricula) {
        return titulacionRepository.existsByMatriculaCodMatricula(codMatricula);
    }

}
