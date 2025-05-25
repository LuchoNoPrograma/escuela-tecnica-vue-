package uap.edu.bo.escuela_tecnica.modulo;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalle;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalleRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;
    private final ModuloMapper moduloMapper;
    private final PlanEstudioDetalleRepository planEstudioDetalleRepository;

    public ModuloService(final ModuloRepository moduloRepository, final ModuloMapper moduloMapper,
            final PlanEstudioDetalleRepository planEstudioDetalleRepository) {
        this.moduloRepository = moduloRepository;
        this.moduloMapper = moduloMapper;
        this.planEstudioDetalleRepository = planEstudioDetalleRepository;
    }

    public List<ModuloDTO> findAll() {
        final List<Modulo> moduloes = moduloRepository.findAll(Sort.by("idModulo"));
        return moduloes.stream()
                .map(modulo -> moduloMapper.updateModuloDTO(modulo, new ModuloDTO()))
                .toList();
    }

    public ModuloDTO get(final Long idModulo) {
        return moduloRepository.findById(idModulo)
                .map(modulo -> moduloMapper.updateModuloDTO(modulo, new ModuloDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ModuloDTO moduloDTO) {
        final Modulo modulo = new Modulo();
        moduloMapper.updateModulo(moduloDTO, modulo);
        return moduloRepository.save(modulo).getIdModulo();
    }

    public void update(final Long idModulo, final ModuloDTO moduloDTO) {
        final Modulo modulo = moduloRepository.findById(idModulo)
                .orElseThrow(NotFoundException::new);
        moduloMapper.updateModulo(moduloDTO, modulo);
        moduloRepository.save(modulo);
    }

    public void delete(final Long idModulo) {
        moduloRepository.deleteById(idModulo);
    }

    public ReferencedWarning getReferencedWarning(final Long idModulo) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Modulo modulo = moduloRepository.findById(idModulo)
                .orElseThrow(NotFoundException::new);
        final PlanEstudioDetalle moduloPlanEstudioDetalle = planEstudioDetalleRepository.findFirstByModulo(modulo);
        if (moduloPlanEstudioDetalle != null) {
            referencedWarning.setKey("modulo.planEstudioDetalle.modulo.referenced");
            referencedWarning.addParam(moduloPlanEstudioDetalle.getIdPlanEstudioDetalle());
            return referencedWarning;
        }
        return null;
    }

}
