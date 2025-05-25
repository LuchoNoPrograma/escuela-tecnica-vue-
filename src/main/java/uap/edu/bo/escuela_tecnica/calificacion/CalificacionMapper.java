package uap.edu.bo.escuela_tecnica.calificacion;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.criterio_eval.CriterioEval;
import uap.edu.bo.escuela_tecnica.criterio_eval.CriterioEvalRepository;
import uap.edu.bo.escuela_tecnica.programacion.Programacion;
import uap.edu.bo.escuela_tecnica.programacion.ProgramacionRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CalificacionMapper {

    @Mapping(target = "criterioEval", ignore = true)
    @Mapping(target = "programacion", ignore = true)
    CalificacionDTO updateCalificacionDTO(Calificacion calificacion,
            @MappingTarget CalificacionDTO calificacionDTO);

    @AfterMapping
    default void afterUpdateCalificacionDTO(Calificacion calificacion,
            @MappingTarget CalificacionDTO calificacionDTO) {
        calificacionDTO.setCriterioEval(calificacion.getCriterioEval() == null ? null : calificacion.getCriterioEval().getIdCriterioEval());
        calificacionDTO.setProgramacion(calificacion.getProgramacion() == null ? null : calificacion.getProgramacion().getIdProgramacion());
    }

    @Mapping(target = "idCalificacion", ignore = true)
    @Mapping(target = "criterioEval", ignore = true)
    @Mapping(target = "programacion", ignore = true)
    Calificacion updateCalificacion(CalificacionDTO calificacionDTO,
            @MappingTarget Calificacion calificacion,
            @Context CriterioEvalRepository criterioEvalRepository,
            @Context ProgramacionRepository programacionRepository);

    @AfterMapping
    default void afterUpdateCalificacion(CalificacionDTO calificacionDTO,
            @MappingTarget Calificacion calificacion,
            @Context CriterioEvalRepository criterioEvalRepository,
            @Context ProgramacionRepository programacionRepository) {
        final CriterioEval criterioEval = calificacionDTO.getCriterioEval() == null ? null : criterioEvalRepository.findById(calificacionDTO.getCriterioEval())
                .orElseThrow(() -> new NotFoundException("criterioEval not found"));
        calificacion.setCriterioEval(criterioEval);
        final Programacion programacion = calificacionDTO.getProgramacion() == null ? null : programacionRepository.findById(calificacionDTO.getProgramacion())
                .orElseThrow(() -> new NotFoundException("programacion not found"));
        calificacion.setProgramacion(programacion);
    }

}
