package uap.edu.bo.escuela_tecnica.preinscripcion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Service
public class PreinscripcionService {

    private final PreinscripcionRepository preinscripcionRepository;
    private final PreinscripcionMapper preinscripcionMapper;

    public PreinscripcionService(final PreinscripcionRepository preinscripcionRepository,
            final PreinscripcionMapper preinscripcionMapper) {
        this.preinscripcionRepository = preinscripcionRepository;
        this.preinscripcionMapper = preinscripcionMapper;
    }

    public Page<PreinscripcionDTO> findAll(final String filter, final Pageable pageable) {
        Page<Preinscripcion> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = preinscripcionRepository.findAllByIdPreinscripcion(longFilter, pageable);
        } else {
            page = preinscripcionRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(preinscripcion -> preinscripcionMapper.updatePreinscripcionDTO(preinscripcion, new PreinscripcionDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    public PreinscripcionDTO get(final Long idPreinscripcion) {
        return preinscripcionRepository.findById(idPreinscripcion)
                .map(preinscripcion -> preinscripcionMapper.updatePreinscripcionDTO(preinscripcion, new PreinscripcionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PreinscripcionDTO preinscripcionDTO) {
        final Preinscripcion preinscripcion = new Preinscripcion();
        preinscripcionMapper.updatePreinscripcion(preinscripcionDTO, preinscripcion);
        return preinscripcionRepository.save(preinscripcion).getIdPreinscripcion();
    }

    public void update(final Long idPreinscripcion, final PreinscripcionDTO preinscripcionDTO) {
        final Preinscripcion preinscripcion = preinscripcionRepository.findById(idPreinscripcion)
                .orElseThrow(NotFoundException::new);
        preinscripcionMapper.updatePreinscripcion(preinscripcionDTO, preinscripcion);
        preinscripcionRepository.save(preinscripcion);
    }

    public void delete(final Long idPreinscripcion) {
        preinscripcionRepository.deleteById(idPreinscripcion);
    }

}
