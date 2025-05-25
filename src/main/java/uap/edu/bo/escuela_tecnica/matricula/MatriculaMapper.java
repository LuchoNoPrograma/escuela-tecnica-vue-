package uap.edu.bo.escuela_tecnica.matricula;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.grupo.Grupo;
import uap.edu.bo.escuela_tecnica.grupo.GrupoRepository;
import uap.edu.bo.escuela_tecnica.persona.Persona;
import uap.edu.bo.escuela_tecnica.persona.PersonaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MatriculaMapper {

    @Mapping(target = "grupo", ignore = true)
    @Mapping(target = "persona", ignore = true)
    MatriculaDTO updateMatriculaDTO(Matricula matricula, @MappingTarget MatriculaDTO matriculaDTO);

    @AfterMapping
    default void afterUpdateMatriculaDTO(Matricula matricula,
            @MappingTarget MatriculaDTO matriculaDTO) {
        matriculaDTO.setGrupo(matricula.getGrupo() == null ? null : matricula.getGrupo().getIdGrupo());
        matriculaDTO.setPersona(matricula.getPersona() == null ? null : matricula.getPersona().getIdPersona());
    }

    @Mapping(target = "codMatricula", ignore = true)
    @Mapping(target = "grupo", ignore = true)
    @Mapping(target = "persona", ignore = true)
    Matricula updateMatricula(MatriculaDTO matriculaDTO, @MappingTarget Matricula matricula,
            @Context GrupoRepository grupoRepository, @Context PersonaRepository personaRepository);

    @AfterMapping
    default void afterUpdateMatricula(MatriculaDTO matriculaDTO, @MappingTarget Matricula matricula,
            @Context GrupoRepository grupoRepository,
            @Context PersonaRepository personaRepository) {
        final Grupo grupo = matriculaDTO.getGrupo() == null ? null : grupoRepository.findById(matriculaDTO.getGrupo())
                .orElseThrow(() -> new NotFoundException("grupo not found"));
        matricula.setGrupo(grupo);
        final Persona persona = matriculaDTO.getPersona() == null ? null : personaRepository.findById(matriculaDTO.getPersona())
                .orElseThrow(() -> new NotFoundException("persona not found"));
        matricula.setPersona(persona);
    }

}
