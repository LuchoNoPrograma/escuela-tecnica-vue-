package uap.edu.bo.escuela_tecnica.modalidad;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.programa.Programa;
import uap.edu.bo.escuela_tecnica.programa.ProgramaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class ModalidadService {

    private final ModalidadRepository modalidadRepository;
    private final ModalidadMapper modalidadMapper;
    private final ProgramaRepository programaRepository;

    public ModalidadService(final ModalidadRepository modalidadRepository,
            final ModalidadMapper modalidadMapper, final ProgramaRepository programaRepository) {
        this.modalidadRepository = modalidadRepository;
        this.modalidadMapper = modalidadMapper;
        this.programaRepository = programaRepository;
    }

    public List<ModalidadDTO> findAll() {
        final List<Modalidad> modalidads = modalidadRepository.findAll(Sort.by("idModalidad"));
        return modalidads.stream()
                .map(modalidad -> modalidadMapper.updateModalidadDTO(modalidad, new ModalidadDTO()))
                .toList();
    }

    public ModalidadDTO get(final Long idModalidad) {
        return modalidadRepository.findById(idModalidad)
                .map(modalidad -> modalidadMapper.updateModalidadDTO(modalidad, new ModalidadDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ModalidadDTO modalidadDTO) {
        final Modalidad modalidad = new Modalidad();
        modalidadMapper.updateModalidad(modalidadDTO, modalidad);
        return modalidadRepository.save(modalidad).getIdModalidad();
    }

    public void update(final Long idModalidad, final ModalidadDTO modalidadDTO) {
        final Modalidad modalidad = modalidadRepository.findById(idModalidad)
                .orElseThrow(NotFoundException::new);
        modalidadMapper.updateModalidad(modalidadDTO, modalidad);
        modalidadRepository.save(modalidad);
    }

    public void delete(final Long idModalidad) {
        modalidadRepository.deleteById(idModalidad);
    }

    public ReferencedWarning getReferencedWarning(final Long idModalidad) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Modalidad modalidad = modalidadRepository.findById(idModalidad)
                .orElseThrow(NotFoundException::new);
        final Programa modalidadPrograma = programaRepository.findFirstByModalidad(modalidad);
        if (modalidadPrograma != null) {
            referencedWarning.setKey("modalidad.programa.modalidad.referenced");
            referencedWarning.addParam(modalidadPrograma.getIdPrograma());
            return referencedWarning;
        }
        return null;
    }

}
