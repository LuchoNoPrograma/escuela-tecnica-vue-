package uap.edu.bo.escuela_tecnica.plan_estudio;

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


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PlanEstudioMapper {

    @Mapping(target = "programa", ignore = true)
    PlanEstudioDTO updatePlanEstudioDTO(PlanEstudio planEstudio,
            @MappingTarget PlanEstudioDTO planEstudioDTO);

    @AfterMapping
    default void afterUpdatePlanEstudioDTO(PlanEstudio planEstudio,
            @MappingTarget PlanEstudioDTO planEstudioDTO) {
        planEstudioDTO.setPrograma(planEstudio.getPrograma() == null ? null : planEstudio.getPrograma().getIdPrograma());
    }

    @Mapping(target = "idPlanEstudio", ignore = true)
    @Mapping(target = "programa", ignore = true)
    PlanEstudio updatePlanEstudio(PlanEstudioDTO planEstudioDTO,
            @MappingTarget PlanEstudio planEstudio, @Context ProgramaRepository programaRepository);

    @AfterMapping
    default void afterUpdatePlanEstudio(PlanEstudioDTO planEstudioDTO,
            @MappingTarget PlanEstudio planEstudio,
            @Context ProgramaRepository programaRepository) {
        final Programa programa = planEstudioDTO.getPrograma() == null ? null : programaRepository.findById(planEstudioDTO.getPrograma())
                .orElseThrow(() -> new NotFoundException("programa not found"));
        planEstudio.setPrograma(programa);
    }

}
