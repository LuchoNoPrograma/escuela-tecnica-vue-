package uap.edu.bo.escuela_tecnica.persona;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.administrativo.Administrativo;
import uap.edu.bo.escuela_tecnica.administrativo.AdministrativoRepository;
import uap.edu.bo.escuela_tecnica.docente.Docente;
import uap.edu.bo.escuela_tecnica.docente.DocenteRepository;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.usuario.Usuario;
import uap.edu.bo.escuela_tecnica.usuario.UsuarioRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;
    private final AdministrativoRepository administrativoRepository;
    private final DocenteRepository docenteRepository;
    private final MatriculaRepository matriculaRepository;
    private final UsuarioRepository usuarioRepository;

    public PersonaService(final PersonaRepository personaRepository,
            final PersonaMapper personaMapper,
            final AdministrativoRepository administrativoRepository,
            final DocenteRepository docenteRepository,
            final MatriculaRepository matriculaRepository,
            final UsuarioRepository usuarioRepository) {
        this.personaRepository = personaRepository;
        this.personaMapper = personaMapper;
        this.administrativoRepository = administrativoRepository;
        this.docenteRepository = docenteRepository;
        this.matriculaRepository = matriculaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<PersonaDTO> findAll() {
        final List<Persona> personae = personaRepository.findAll(Sort.by("idPersona"));
        return personae.stream()
                .map(persona -> personaMapper.updatePersonaDTO(persona, new PersonaDTO()))
                .toList();
    }

    public PersonaDTO get(final Long idPersona) {
        return personaRepository.findById(idPersona)
                .map(persona -> personaMapper.updatePersonaDTO(persona, new PersonaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PersonaDTO personaDTO) {
        final Persona persona = new Persona();
        personaMapper.updatePersona(personaDTO, persona);
        return personaRepository.save(persona).getIdPersona();
    }

    public void update(final Long idPersona, final PersonaDTO personaDTO) {
        final Persona persona = personaRepository.findById(idPersona)
                .orElseThrow(NotFoundException::new);
        personaMapper.updatePersona(personaDTO, persona);
        personaRepository.save(persona);
    }

    public void delete(final Long idPersona) {
        personaRepository.deleteById(idPersona);
    }

    public ReferencedWarning getReferencedWarning(final Long idPersona) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Persona persona = personaRepository.findById(idPersona)
                .orElseThrow(NotFoundException::new);
        final Administrativo personaAdministrativo = administrativoRepository.findFirstByPersona(persona);
        if (personaAdministrativo != null) {
            referencedWarning.setKey("persona.administrativo.persona.referenced");
            referencedWarning.addParam(personaAdministrativo.getIdAdministrativo());
            return referencedWarning;
        }
        final Docente personaDocente = docenteRepository.findFirstByPersona(persona);
        if (personaDocente != null) {
            referencedWarning.setKey("persona.docente.persona.referenced");
            referencedWarning.addParam(personaDocente.getIdDocente());
            return referencedWarning;
        }
        final Matricula personaMatricula = matriculaRepository.findFirstByPersona(persona);
        if (personaMatricula != null) {
            referencedWarning.setKey("persona.matricula.persona.referenced");
            referencedWarning.addParam(personaMatricula.getCodMatricula());
            return referencedWarning;
        }
        final Usuario personaUsuario = usuarioRepository.findFirstByPersona(persona);
        if (personaUsuario != null) {
            referencedWarning.setKey("persona.usuario.persona.referenced");
            referencedWarning.addParam(personaUsuario.getIdUsuario());
            return referencedWarning;
        }
        return null;
    }

}
