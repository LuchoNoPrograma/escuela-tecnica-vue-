package uap.edu.bo.escuela_tecnica.nivel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NivelMapper {

    NivelDTO updateNivelDTO(Nivel nivel, @MappingTarget NivelDTO nivelDTO);

    @Mapping(target = "idNivel", ignore = true)
    Nivel updateNivel(NivelDTO nivelDTO, @MappingTarget Nivel nivel);

}
