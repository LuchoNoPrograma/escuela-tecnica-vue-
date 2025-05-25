package uap.edu.bo.escuela_tecnica.ocupa;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.rol.Rol;
import uap.edu.bo.escuela_tecnica.rol.RolRepository;
import uap.edu.bo.escuela_tecnica.usuario.Usuario;
import uap.edu.bo.escuela_tecnica.usuario.UsuarioRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OcupaMapper {

    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    OcupaDTO updateOcupaDTO(Ocupa ocupa, @MappingTarget OcupaDTO ocupaDTO);

    @AfterMapping
    default void afterUpdateOcupaDTO(Ocupa ocupa, @MappingTarget OcupaDTO ocupaDTO) {
        ocupaDTO.setRol(ocupa.getRol() == null ? null : ocupa.getRol().getIdRol());
        ocupaDTO.setUsuario(ocupa.getUsuario() == null ? null : ocupa.getUsuario().getIdUsuario());
    }

    @Mapping(target = "estOcupa", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Ocupa updateOcupa(OcupaDTO ocupaDTO, @MappingTarget Ocupa ocupa,
            @Context RolRepository rolRepository, @Context UsuarioRepository usuarioRepository);

    @AfterMapping
    default void afterUpdateOcupa(OcupaDTO ocupaDTO, @MappingTarget Ocupa ocupa,
            @Context RolRepository rolRepository, @Context UsuarioRepository usuarioRepository) {
        final Rol rol = ocupaDTO.getRol() == null ? null : rolRepository.findById(ocupaDTO.getRol())
                .orElseThrow(() -> new NotFoundException("rol not found"));
        ocupa.setRol(rol);
        final Usuario usuario = ocupaDTO.getUsuario() == null ? null : usuarioRepository.findById(ocupaDTO.getUsuario())
                .orElseThrow(() -> new NotFoundException("usuario not found"));
        ocupa.setUsuario(usuario);
    }

}
