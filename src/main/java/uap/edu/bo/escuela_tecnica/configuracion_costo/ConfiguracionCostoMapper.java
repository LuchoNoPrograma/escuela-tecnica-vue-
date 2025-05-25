package uap.edu.bo.escuela_tecnica.configuracion_costo;

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
public interface ConfiguracionCostoMapper {

    @Mapping(target = "programa", ignore = true)
    ConfiguracionCostoDTO updateConfiguracionCostoDTO(ConfiguracionCosto configuracionCosto,
            @MappingTarget ConfiguracionCostoDTO configuracionCostoDTO);

    @AfterMapping
    default void afterUpdateConfiguracionCostoDTO(ConfiguracionCosto configuracionCosto,
            @MappingTarget ConfiguracionCostoDTO configuracionCostoDTO) {
        configuracionCostoDTO.setPrograma(configuracionCosto.getPrograma() == null ? null : configuracionCosto.getPrograma().getIdPrograma());
    }

    @Mapping(target = "idConfiguracionCosto", ignore = true)
    @Mapping(target = "programa", ignore = true)
    ConfiguracionCosto updateConfiguracionCosto(ConfiguracionCostoDTO configuracionCostoDTO,
            @MappingTarget ConfiguracionCosto configuracionCosto,
            @Context ProgramaRepository programaRepository);

    @AfterMapping
    default void afterUpdateConfiguracionCosto(ConfiguracionCostoDTO configuracionCostoDTO,
            @MappingTarget ConfiguracionCosto configuracionCosto,
            @Context ProgramaRepository programaRepository) {
        final Programa programa = configuracionCostoDTO.getPrograma() == null ? null : programaRepository.findById(configuracionCostoDTO.getPrograma())
                .orElseThrow(() -> new NotFoundException("programa not found"));
        configuracionCosto.setPrograma(programa);
    }

}
