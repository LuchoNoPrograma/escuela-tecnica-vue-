package uap.edu.bo.escuela_tecnica.nivel;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalle;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalleRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class NivelService {

    private final NivelRepository nivelRepository;
    private final NivelMapper nivelMapper;
    private final PlanEstudioDetalleRepository planEstudioDetalleRepository;

    public NivelService(final NivelRepository nivelRepository, final NivelMapper nivelMapper,
            final PlanEstudioDetalleRepository planEstudioDetalleRepository) {
        this.nivelRepository = nivelRepository;
        this.nivelMapper = nivelMapper;
        this.planEstudioDetalleRepository = planEstudioDetalleRepository;
    }

    public List<NivelDTO> findAll() {
        final List<Nivel> nivels = nivelRepository.findAll(Sort.by("idNivel"));
        return nivels.stream()
                .map(nivel -> nivelMapper.updateNivelDTO(nivel, new NivelDTO()))
                .toList();
    }

    public NivelDTO get(final Long idNivel) {
        return nivelRepository.findById(idNivel)
                .map(nivel -> nivelMapper.updateNivelDTO(nivel, new NivelDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final NivelDTO nivelDTO) {
        final Nivel nivel = new Nivel();
        nivelMapper.updateNivel(nivelDTO, nivel);
        return nivelRepository.save(nivel).getIdNivel();
    }

    public void update(final Long idNivel, final NivelDTO nivelDTO) {
        final Nivel nivel = nivelRepository.findById(idNivel)
                .orElseThrow(NotFoundException::new);
        nivelMapper.updateNivel(nivelDTO, nivel);
        nivelRepository.save(nivel);
    }

    public void delete(final Long idNivel) {
        nivelRepository.deleteById(idNivel);
    }

    public ReferencedWarning getReferencedWarning(final Long idNivel) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Nivel nivel = nivelRepository.findById(idNivel)
                .orElseThrow(NotFoundException::new);
        final PlanEstudioDetalle nivelPlanEstudioDetalle = planEstudioDetalleRepository.findFirstByNivel(nivel);
        if (nivelPlanEstudioDetalle != null) {
            referencedWarning.setKey("nivel.planEstudioDetalle.nivel.referenced");
            referencedWarning.addParam(nivelPlanEstudioDetalle.getIdPlanEstudioDetalle());
            return referencedWarning;
        }
        return null;
    }

}
