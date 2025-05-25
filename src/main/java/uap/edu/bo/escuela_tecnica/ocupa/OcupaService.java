package uap.edu.bo.escuela_tecnica.ocupa;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.rol.RolRepository;
import uap.edu.bo.escuela_tecnica.usuario.UsuarioRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Service
public class OcupaService {

    private final OcupaRepository ocupaRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final OcupaMapper ocupaMapper;

    public OcupaService(final OcupaRepository ocupaRepository, final RolRepository rolRepository,
            final UsuarioRepository usuarioRepository, final OcupaMapper ocupaMapper) {
        this.ocupaRepository = ocupaRepository;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.ocupaMapper = ocupaMapper;
    }

    public List<OcupaDTO> findAll() {
        final List<Ocupa> ocupas = ocupaRepository.findAll(Sort.by("estOcupa"));
        return ocupas.stream()
                .map(ocupa -> ocupaMapper.updateOcupaDTO(ocupa, new OcupaDTO()))
                .toList();
    }

    public OcupaDTO get(final String estOcupa) {
        return ocupaRepository.findById(estOcupa)
                .map(ocupa -> ocupaMapper.updateOcupaDTO(ocupa, new OcupaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final OcupaDTO ocupaDTO) {
        final Ocupa ocupa = new Ocupa();
        ocupaMapper.updateOcupa(ocupaDTO, ocupa, rolRepository, usuarioRepository);
        ocupa.setEstOcupa(ocupaDTO.getEstOcupa());
        return ocupaRepository.save(ocupa).getEstOcupa();
    }

    public void update(final String estOcupa, final OcupaDTO ocupaDTO) {
        final Ocupa ocupa = ocupaRepository.findById(estOcupa)
                .orElseThrow(NotFoundException::new);
        ocupaMapper.updateOcupa(ocupaDTO, ocupa, rolRepository, usuarioRepository);
        ocupaRepository.save(ocupa);
    }

    public void delete(final String estOcupa) {
        ocupaRepository.deleteById(estOcupa);
    }

    public boolean estOcupaExists(final String estOcupa) {
        return ocupaRepository.existsByEstOcupaIgnoreCase(estOcupa);
    }

}
