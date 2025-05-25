package uap.edu.bo.escuela_tecnica.criterio_eval;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModulo;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModuloRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CriterioEvalMapper {

    @Mapping(target = "cronogramaMod", ignore = true)
    CriterioEvalDTO updateCriterioEvalDTO(CriterioEval criterioEval,
            @MappingTarget CriterioEvalDTO criterioEvalDTO);

    @AfterMapping
    default void afterUpdateCriterioEvalDTO(CriterioEval criterioEval,
            @MappingTarget CriterioEvalDTO criterioEvalDTO) {
        criterioEvalDTO.setCronogramaMod(criterioEval.getCronogramaMod() == null ? null : criterioEval.getCronogramaMod().getIdCronogramaMod());
    }

    @Mapping(target = "idCriterioEval", ignore = true)
    @Mapping(target = "cronogramaMod", ignore = true)
    CriterioEval updateCriterioEval(CriterioEvalDTO criterioEvalDTO,
            @MappingTarget CriterioEval criterioEval,
            @Context CronogramaModuloRepository cronogramaModuloRepository);

    @AfterMapping
    default void afterUpdateCriterioEval(CriterioEvalDTO criterioEvalDTO,
            @MappingTarget CriterioEval criterioEval,
            @Context CronogramaModuloRepository cronogramaModuloRepository) {
        final CronogramaModulo cronogramaMod = criterioEvalDTO.getCronogramaMod() == null ? null : cronogramaModuloRepository.findById(criterioEvalDTO.getCronogramaMod())
                .orElseThrow(() -> new NotFoundException("cronogramaMod not found"));
        criterioEval.setCronogramaMod(cronogramaMod);
    }

}
