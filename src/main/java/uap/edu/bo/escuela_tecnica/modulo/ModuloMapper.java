package uap.edu.bo.escuela_tecnica.modulo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ModuloMapper {

    ModuloDTO updateModuloDTO(Modulo modulo, @MappingTarget ModuloDTO moduloDTO);

    @Mapping(target = "idModulo", ignore = true)
    Modulo updateModulo(ModuloDTO moduloDTO, @MappingTarget Modulo modulo);

}
