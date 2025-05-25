package uap.edu.bo.escuela_tecnica.persona;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PersonaMapper {

    PersonaDTO updatePersonaDTO(Persona persona, @MappingTarget PersonaDTO personaDTO);

    @Mapping(target = "idPersona", ignore = true)
    Persona updatePersona(PersonaDTO personaDTO, @MappingTarget Persona persona);

}
