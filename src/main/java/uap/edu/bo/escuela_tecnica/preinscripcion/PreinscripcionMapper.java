package uap.edu.bo.escuela_tecnica.preinscripcion;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PreinscripcionMapper {

    PreinscripcionDTO updatePreinscripcionDTO(Preinscripcion preinscripcion,
            @MappingTarget PreinscripcionDTO preinscripcionDTO);

    @Mapping(target = "idPreinscripcion", ignore = true)
    Preinscripcion updatePreinscripcion(PreinscripcionDTO preinscripcionDTO,
            @MappingTarget Preinscripcion preinscripcion);

}
