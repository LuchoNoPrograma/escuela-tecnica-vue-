package uap.edu.bo.escuela_tecnica.usuario;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.ocupa.Ocupa;
import uap.edu.bo.escuela_tecnica.ocupa.OcupaRepository;
import uap.edu.bo.escuela_tecnica.persona.PersonaRepository;
import uap.edu.bo.escuela_tecnica.tarea.TareaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TareaRepository tareaRepository;
    private final PersonaRepository personaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;
    private final OcupaRepository ocupaRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository,
            final TareaRepository tareaRepository, final PersonaRepository personaRepository,
            final PasswordEncoder passwordEncoder, final UsuarioMapper usuarioMapper,
            final OcupaRepository ocupaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tareaRepository = tareaRepository;
        this.personaRepository = personaRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
        this.ocupaRepository = ocupaRepository;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("idUsuario"));
        return usuarios.stream()
                .map(usuario -> usuarioMapper.updateUsuarioDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .map(usuario -> usuarioMapper.updateUsuarioDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        usuarioMapper.updateUsuario(usuarioDTO, usuario, tareaRepository, personaRepository, passwordEncoder);
        return usuarioRepository.save(usuario).getIdUsuario();
    }

    public void update(final Long idUsuario, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(NotFoundException::new);
        usuarioMapper.updateUsuario(usuarioDTO, usuario, tareaRepository, personaRepository, passwordEncoder);
        usuarioRepository.save(usuario);
    }

    public void delete(final Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    public boolean personaExists(final Long idPersona) {
        return usuarioRepository.existsByPersonaIdPersona(idPersona);
    }

    public ReferencedWarning getReferencedWarning(final Long idUsuario) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(NotFoundException::new);
        final Ocupa usuarioOcupa = ocupaRepository.findFirstByUsuario(usuario);
        if (usuarioOcupa != null) {
            referencedWarning.setKey("usuario.ocupa.usuario.referenced");
            referencedWarning.addParam(usuarioOcupa.getEstOcupa());
            return referencedWarning;
        }
        return null;
    }

}
