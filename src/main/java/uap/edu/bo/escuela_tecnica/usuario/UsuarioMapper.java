package uap.edu.bo.escuela_tecnica.usuario;

import java.util.HashSet;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import uap.edu.bo.escuela_tecnica.persona.Persona;
import uap.edu.bo.escuela_tecnica.persona.PersonaRepository;
import uap.edu.bo.escuela_tecnica.tarea.Tarea;
import uap.edu.bo.escuela_tecnica.tarea.TareaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UsuarioMapper {

    @Mapping(target = "listaTareasAsignadas", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "contrasenaHash", ignore = true)
    UsuarioDTO updateUsuarioDTO(Usuario usuario, @MappingTarget UsuarioDTO usuarioDTO);

    @AfterMapping
    default void afterUpdateUsuarioDTO(Usuario usuario, @MappingTarget UsuarioDTO usuarioDTO) {
        usuarioDTO.setListaTareasAsignadas(usuario.getListaTareasAsignadas().stream()
                .map(tarea -> tarea.getIdTarea())
                .toList());
        usuarioDTO.setPersona(usuario.getPersona() == null ? null : usuario.getPersona().getIdPersona());
    }

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "listaTareasAsignadas", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "contrasenaHash", ignore = true)
    Usuario updateUsuario(UsuarioDTO usuarioDTO, @MappingTarget Usuario usuario,
            @Context TareaRepository tareaRepository, @Context PersonaRepository personaRepository,
            @Context PasswordEncoder passwordEncoder);

    @AfterMapping
    default void afterUpdateUsuario(UsuarioDTO usuarioDTO, @MappingTarget Usuario usuario,
            @Context TareaRepository tareaRepository, @Context PersonaRepository personaRepository,
            @Context PasswordEncoder passwordEncoder) {
        final List<Tarea> listaTareasAsignadas = tareaRepository.findAllById(
                usuarioDTO.getListaTareasAsignadas() == null ? List.of() : usuarioDTO.getListaTareasAsignadas());
        if (listaTareasAsignadas.size() != (usuarioDTO.getListaTareasAsignadas() == null ? 0 : usuarioDTO.getListaTareasAsignadas().size())) {
            throw new NotFoundException("one of listaTareasAsignadas not found");
        }
        usuario.setListaTareasAsignadas(new HashSet<>(listaTareasAsignadas));
        final Persona persona = usuarioDTO.getPersona() == null ? null : personaRepository.findById(usuarioDTO.getPersona())
                .orElseThrow(() -> new NotFoundException("persona not found"));
        usuario.setPersona(persona);
        usuario.setContrasenaHash(passwordEncoder.encode(usuarioDTO.getContrasenaHash()));
    }

}
