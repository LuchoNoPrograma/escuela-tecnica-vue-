package uap.edu.bo.escuela_tecnica.detalle_pago;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.plan_pago.PlanPago;
import uap.edu.bo.escuela_tecnica.plan_pago.PlanPagoRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DetallePagoMapper {

    @Mapping(target = "planPago", ignore = true)
    DetallePagoDTO updateDetallePagoDTO(DetallePago detallePago,
            @MappingTarget DetallePagoDTO detallePagoDTO);

    @AfterMapping
    default void afterUpdateDetallePagoDTO(DetallePago detallePago,
            @MappingTarget DetallePagoDTO detallePagoDTO) {
        detallePagoDTO.setPlanPago(detallePago.getPlanPago() == null ? null : detallePago.getPlanPago().getIdPlanPago());
    }

    @Mapping(target = "idDetallePago", ignore = true)
    @Mapping(target = "planPago", ignore = true)
    DetallePago updateDetallePago(DetallePagoDTO detallePagoDTO,
            @MappingTarget DetallePago detallePago, @Context PlanPagoRepository planPagoRepository);

    @AfterMapping
    default void afterUpdateDetallePago(DetallePagoDTO detallePagoDTO,
            @MappingTarget DetallePago detallePago,
            @Context PlanPagoRepository planPagoRepository) {
        final PlanPago planPago = detallePagoDTO.getPlanPago() == null ? null : planPagoRepository.findById(detallePagoDTO.getPlanPago())
                .orElseThrow(() -> new NotFoundException("planPago not found"));
        detallePago.setPlanPago(planPago);
    }

}
