package uap.edu.bo.escuela_tecnica.plan_pago;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PlanPagoMapper {

    @Mapping(target = "matricula", ignore = true)
    PlanPagoDTO updatePlanPagoDTO(PlanPago planPago, @MappingTarget PlanPagoDTO planPagoDTO);

    @AfterMapping
    default void afterUpdatePlanPagoDTO(PlanPago planPago, @MappingTarget PlanPagoDTO planPagoDTO) {
        planPagoDTO.setMatricula(planPago.getMatricula() == null ? null : planPago.getMatricula().getCodMatricula());
    }

    @Mapping(target = "idPlanPago", ignore = true)
    @Mapping(target = "matricula", ignore = true)
    PlanPago updatePlanPago(PlanPagoDTO planPagoDTO, @MappingTarget PlanPago planPago,
            @Context MatriculaRepository matriculaRepository);

    @AfterMapping
    default void afterUpdatePlanPago(PlanPagoDTO planPagoDTO, @MappingTarget PlanPago planPago,
            @Context MatriculaRepository matriculaRepository) {
        final Matricula matricula = planPagoDTO.getMatricula() == null ? null : matriculaRepository.findById(planPagoDTO.getMatricula())
                .orElseThrow(() -> new NotFoundException("matricula not found"));
        planPago.setMatricula(matricula);
    }

}
