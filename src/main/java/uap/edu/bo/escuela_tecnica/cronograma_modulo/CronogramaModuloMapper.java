package uap.edu.bo.escuela_tecnica.cronograma_modulo;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.docente.Docente;
import uap.edu.bo.escuela_tecnica.docente.DocenteRepository;
import uap.edu.bo.escuela_tecnica.grupo.Grupo;
import uap.edu.bo.escuela_tecnica.grupo.GrupoRepository;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalle;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalleRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CronogramaModuloMapper {

    @Mapping(target = "planEstudioDetalle", ignore = true)
    @Mapping(target = "grupo", ignore = true)
    @Mapping(target = "docente", ignore = true)
    CronogramaModuloDTO updateCronogramaModuloDTO(CronogramaModulo cronogramaModulo,
            @MappingTarget CronogramaModuloDTO cronogramaModuloDTO);

    @AfterMapping
    default void afterUpdateCronogramaModuloDTO(CronogramaModulo cronogramaModulo,
            @MappingTarget CronogramaModuloDTO cronogramaModuloDTO) {
        cronogramaModuloDTO.setPlanEstudioDetalle(cronogramaModulo.getPlanEstudioDetalle() == null ? null : cronogramaModulo.getPlanEstudioDetalle().getIdPlanEstudioDetalle());
        cronogramaModuloDTO.setGrupo(cronogramaModulo.getGrupo() == null ? null : cronogramaModulo.getGrupo().getIdGrupo());
        cronogramaModuloDTO.setDocente(cronogramaModulo.getDocente() == null ? null : cronogramaModulo.getDocente().getIdDocente());
    }

    @Mapping(target = "idCronogramaMod", ignore = true)
    @Mapping(target = "planEstudioDetalle", ignore = true)
    @Mapping(target = "grupo", ignore = true)
    @Mapping(target = "docente", ignore = true)
    CronogramaModulo updateCronogramaModulo(CronogramaModuloDTO cronogramaModuloDTO,
            @MappingTarget CronogramaModulo cronogramaModulo,
            @Context PlanEstudioDetalleRepository planEstudioDetalleRepository,
            @Context GrupoRepository grupoRepository, @Context DocenteRepository docenteRepository);

    @AfterMapping
    default void afterUpdateCronogramaModulo(CronogramaModuloDTO cronogramaModuloDTO,
            @MappingTarget CronogramaModulo cronogramaModulo,
            @Context PlanEstudioDetalleRepository planEstudioDetalleRepository,
            @Context GrupoRepository grupoRepository,
            @Context DocenteRepository docenteRepository) {
        final PlanEstudioDetalle planEstudioDetalle = cronogramaModuloDTO.getPlanEstudioDetalle() == null ? null : planEstudioDetalleRepository.findById(cronogramaModuloDTO.getPlanEstudioDetalle())
                .orElseThrow(() -> new NotFoundException("planEstudioDetalle not found"));
        cronogramaModulo.setPlanEstudioDetalle(planEstudioDetalle);
        final Grupo grupo = cronogramaModuloDTO.getGrupo() == null ? null : grupoRepository.findById(cronogramaModuloDTO.getGrupo())
                .orElseThrow(() -> new NotFoundException("grupo not found"));
        cronogramaModulo.setGrupo(grupo);
        final Docente docente = cronogramaModuloDTO.getDocente() == null ? null : docenteRepository.findById(cronogramaModuloDTO.getDocente())
                .orElseThrow(() -> new NotFoundException("docente not found"));
        cronogramaModulo.setDocente(docente);
    }

}
