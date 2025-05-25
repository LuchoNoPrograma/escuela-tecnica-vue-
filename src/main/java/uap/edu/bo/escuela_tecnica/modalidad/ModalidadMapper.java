package uap.edu.bo.escuela_tecnica.modalidad;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ModalidadMapper {

    ModalidadDTO updateModalidadDTO(Modalidad modalidad, @MappingTarget ModalidadDTO modalidadDTO);

    @Mapping(target = "idModalidad", ignore = true)
    Modalidad updateModalidad(ModalidadDTO modalidadDTO, @MappingTarget Modalidad modalidad);

}
