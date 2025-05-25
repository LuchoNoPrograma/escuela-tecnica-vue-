package uap.edu.bo.escuela_tecnica.administrativo;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.persona.Persona;
import uap.edu.bo.escuela_tecnica.persona.PersonaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AdministrativoMapper {

    @Mapping(target = "persona", ignore = true)
    AdministrativoDTO updateAdministrativoDTO(Administrativo administrativo,
            @MappingTarget AdministrativoDTO administrativoDTO);

    @AfterMapping
    default void afterUpdateAdministrativoDTO(Administrativo administrativo,
            @MappingTarget AdministrativoDTO administrativoDTO) {
        administrativoDTO.setPersona(administrativo.getPersona() == null ? null : administrativo.getPersona().getIdPersona());
    }

    @Mapping(target = "idAdministrativo", ignore = true)
    @Mapping(target = "persona", ignore = true)
    Administrativo updateAdministrativo(AdministrativoDTO administrativoDTO,
            @MappingTarget Administrativo administrativo,
            @Context PersonaRepository personaRepository);

    @AfterMapping
    default void afterUpdateAdministrativo(AdministrativoDTO administrativoDTO,
            @MappingTarget Administrativo administrativo,
            @Context PersonaRepository personaRepository) {
        final Persona persona = administrativoDTO.getPersona() == null ? null : personaRepository.findById(administrativoDTO.getPersona())
                .orElseThrow(() -> new NotFoundException("persona not found"));
        administrativo.setPersona(persona);
    }

}
