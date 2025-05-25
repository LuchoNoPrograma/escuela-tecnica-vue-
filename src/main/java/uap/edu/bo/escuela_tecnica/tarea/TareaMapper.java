package uap.edu.bo.escuela_tecnica.tarea;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.rol.Rol;
import uap.edu.bo.escuela_tecnica.rol.RolRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TareaMapper {

    @Mapping(target = "agrupa", ignore = true)
    TareaDTO updateTareaDTO(Tarea tarea, @MappingTarget TareaDTO tareaDTO);

    @AfterMapping
    default void afterUpdateTareaDTO(Tarea tarea, @MappingTarget TareaDTO tareaDTO) {
        tareaDTO.setAgrupa(tarea.getRol() == null ? null : tarea.getRol().getIdRol());
    }

    @Mapping(target = "idTarea", ignore = true)
    @Mapping(target = "rol", ignore = true)
    Tarea updateTarea(TareaDTO tareaDTO, @MappingTarget Tarea tarea,
            @Context RolRepository rolRepository);

    @AfterMapping
    default void afterUpdateTarea(TareaDTO tareaDTO, @MappingTarget Tarea tarea,
            @Context RolRepository rolRepository) {
        final Rol agrupa = tareaDTO.getAgrupa() == null ? null : rolRepository.findById(tareaDTO.getAgrupa())
                .orElseThrow(() -> new NotFoundException("agrupa not found"));
        tarea.setRol(agrupa);
    }

}
