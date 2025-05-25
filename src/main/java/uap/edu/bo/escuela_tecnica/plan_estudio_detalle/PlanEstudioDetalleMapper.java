package uap.edu.bo.escuela_tecnica.plan_estudio_detalle;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.modulo.Modulo;
import uap.edu.bo.escuela_tecnica.modulo.ModuloRepository;
import uap.edu.bo.escuela_tecnica.nivel.Nivel;
import uap.edu.bo.escuela_tecnica.nivel.NivelRepository;
import uap.edu.bo.escuela_tecnica.plan_estudio.PlanEstudio;
import uap.edu.bo.escuela_tecnica.plan_estudio.PlanEstudioRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PlanEstudioDetalleMapper {

    @Mapping(target = "nivel", ignore = true)
    @Mapping(target = "modulo", ignore = true)
    @Mapping(target = "planEstudio", ignore = true)
    PlanEstudioDetalleDTO updatePlanEstudioDetalleDTO(PlanEstudioDetalle planEstudioDetalle,
            @MappingTarget PlanEstudioDetalleDTO planEstudioDetalleDTO);

    @AfterMapping
    default void afterUpdatePlanEstudioDetalleDTO(PlanEstudioDetalle planEstudioDetalle,
            @MappingTarget PlanEstudioDetalleDTO planEstudioDetalleDTO) {
        planEstudioDetalleDTO.setNivel(planEstudioDetalle.getNivel() == null ? null : planEstudioDetalle.getNivel().getIdNivel());
        planEstudioDetalleDTO.setModulo(planEstudioDetalle.getModulo() == null ? null : planEstudioDetalle.getModulo().getIdModulo());
        planEstudioDetalleDTO.setPlanEstudio(planEstudioDetalle.getPlanEstudio() == null ? null : planEstudioDetalle.getPlanEstudio().getIdPlanEstudio());
    }

    @Mapping(target = "idPlanEstudioDetalle", ignore = true)
    @Mapping(target = "nivel", ignore = true)
    @Mapping(target = "modulo", ignore = true)
    @Mapping(target = "planEstudio", ignore = true)
    PlanEstudioDetalle updatePlanEstudioDetalle(PlanEstudioDetalleDTO planEstudioDetalleDTO,
            @MappingTarget PlanEstudioDetalle planEstudioDetalle,
            @Context NivelRepository nivelRepository, @Context ModuloRepository moduloRepository,
            @Context PlanEstudioRepository planEstudioRepository);

    @AfterMapping
    default void afterUpdatePlanEstudioDetalle(PlanEstudioDetalleDTO planEstudioDetalleDTO,
            @MappingTarget PlanEstudioDetalle planEstudioDetalle,
            @Context NivelRepository nivelRepository, @Context ModuloRepository moduloRepository,
            @Context PlanEstudioRepository planEstudioRepository) {
        final Nivel nivel = planEstudioDetalleDTO.getNivel() == null ? null : nivelRepository.findById(planEstudioDetalleDTO.getNivel())
                .orElseThrow(() -> new NotFoundException("nivel not found"));
        planEstudioDetalle.setNivel(nivel);
        final Modulo modulo = planEstudioDetalleDTO.getModulo() == null ? null : moduloRepository.findById(planEstudioDetalleDTO.getModulo())
                .orElseThrow(() -> new NotFoundException("modulo not found"));
        planEstudioDetalle.setModulo(modulo);
        final PlanEstudio planEstudio = planEstudioDetalleDTO.getPlanEstudio() == null ? null : planEstudioRepository.findById(planEstudioDetalleDTO.getPlanEstudio())
                .orElseThrow(() -> new NotFoundException("planEstudio not found"));
        planEstudioDetalle.setPlanEstudio(planEstudio);
    }

}
