package uap.edu.bo.escuela_tecnica.rol;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RolMapper {

    RolDTO updateRolDTO(Rol rol, @MappingTarget RolDTO rolDTO);

    @Mapping(target = "idRol", ignore = true)
    Rol updateRol(RolDTO rolDTO, @MappingTarget Rol rol);

}
