package uap.edu.bo.escuela_tecnica.rol;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.ocupa.Ocupa;
import uap.edu.bo.escuela_tecnica.ocupa.OcupaRepository;
import uap.edu.bo.escuela_tecnica.tarea.Tarea;
import uap.edu.bo.escuela_tecnica.tarea.TareaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;
    private final OcupaRepository ocupaRepository;
    private final TareaRepository tareaRepository;

    public RolService(final RolRepository rolRepository, final RolMapper rolMapper,
            final OcupaRepository ocupaRepository, final TareaRepository tareaRepository) {
        this.rolRepository = rolRepository;
        this.rolMapper = rolMapper;
        this.ocupaRepository = ocupaRepository;
        this.tareaRepository = tareaRepository;
    }

    public List<RolDTO> findAll() {
        final List<Rol> rols = rolRepository.findAll(Sort.by("idPermiso"));
        return rols.stream()
                .map(rol -> rolMapper.updateRolDTO(rol, new RolDTO()))
                .toList();
    }

    public RolDTO get(final Long idPermiso) {
        return rolRepository.findById(idPermiso)
                .map(rol -> rolMapper.updateRolDTO(rol, new RolDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RolDTO rolDTO) {
        final Rol rol = new Rol();
        rolMapper.updateRol(rolDTO, rol);
        return rolRepository.save(rol).getIdRol();
    }

    public void update(final Long idPermiso, final RolDTO rolDTO) {
        final Rol rol = rolRepository.findById(idPermiso)
                .orElseThrow(NotFoundException::new);
        rolMapper.updateRol(rolDTO, rol);
        rolRepository.save(rol);
    }

    public void delete(final Long idPermiso) {
        rolRepository.deleteById(idPermiso);
    }

    public ReferencedWarning getReferencedWarning(final Long idPermiso) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Rol rol = rolRepository.findById(idPermiso)
                .orElseThrow(NotFoundException::new);
        final Ocupa rolOcupa = ocupaRepository.findFirstByRol(rol);
        if (rolOcupa != null) {
            referencedWarning.setKey("rol.ocupa.rol.referenced");
            referencedWarning.addParam(rolOcupa.getEstOcupa());
            return referencedWarning;
        }
        final Tarea agrupaTarea = tareaRepository.findFirstByRol(rol);
        if (agrupaTarea != null) {
            referencedWarning.setKey("rol.tarea.agrupa.referenced");
            referencedWarning.addParam(agrupaTarea.getIdTarea());
            return referencedWarning;
        }
        return null;
    }

}
