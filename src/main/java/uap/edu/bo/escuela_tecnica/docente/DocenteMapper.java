package uap.edu.bo.escuela_tecnica.docente;

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
public interface DocenteMapper {

    @Mapping(target = "persona", ignore = true)
    DocenteDTO updateDocenteDTO(Docente docente, @MappingTarget DocenteDTO docenteDTO);

    @AfterMapping
    default void afterUpdateDocenteDTO(Docente docente, @MappingTarget DocenteDTO docenteDTO) {
        docenteDTO.setPersona(docente.getPersona() == null ? null : docente.getPersona().getIdPersona());
    }

    @Mapping(target = "idDocente", ignore = true)
    @Mapping(target = "persona", ignore = true)
    Docente updateDocente(DocenteDTO docenteDTO, @MappingTarget Docente docente,
            @Context PersonaRepository personaRepository);

    @AfterMapping
    default void afterUpdateDocente(DocenteDTO docenteDTO, @MappingTarget Docente docente,
            @Context PersonaRepository personaRepository) {
        final Persona persona = docenteDTO.getPersona() == null ? null : personaRepository.findById(docenteDTO.getPersona())
                .orElseThrow(() -> new NotFoundException("persona not found"));
        docente.setPersona(persona);
    }

}
