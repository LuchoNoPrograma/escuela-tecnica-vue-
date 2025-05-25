package uap.edu.bo.escuela_tecnica.grupo;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.programa.Programa;
import uap.edu.bo.escuela_tecnica.programa.ProgramaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.version.Version;
import uap.edu.bo.escuela_tecnica.version.VersionRepository;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GrupoMapper {

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "programa", ignore = true)
    GrupoDTO updateGrupoDTO(Grupo grupo, @MappingTarget GrupoDTO grupoDTO);

    @AfterMapping
    default void afterUpdateGrupoDTO(Grupo grupo, @MappingTarget GrupoDTO grupoDTO) {
        grupoDTO.setVersion(grupo.getVersion() == null ? null : grupo.getVersion().getIdVersion());
        grupoDTO.setPrograma(grupo.getPrograma() == null ? null : grupo.getPrograma().getIdPrograma());
    }

    @Mapping(target = "idGrupo", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "programa", ignore = true)
    Grupo updateGrupo(GrupoDTO grupoDTO, @MappingTarget Grupo grupo,
            @Context VersionRepository versionRepository,
            @Context ProgramaRepository programaRepository);

    @AfterMapping
    default void afterUpdateGrupo(GrupoDTO grupoDTO, @MappingTarget Grupo grupo,
            @Context VersionRepository versionRepository,
            @Context ProgramaRepository programaRepository) {
        final Version version = grupoDTO.getVersion() == null ? null : versionRepository.findById(grupoDTO.getVersion())
                .orElseThrow(() -> new NotFoundException("version not found"));
        grupo.setVersion(version);
        final Programa programa = grupoDTO.getPrograma() == null ? null : programaRepository.findById(grupoDTO.getPrograma())
                .orElseThrow(() -> new NotFoundException("programa not found"));
        grupo.setPrograma(programa);
    }

}
