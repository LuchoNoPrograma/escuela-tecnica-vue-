package uap.edu.bo.escuela_tecnica.monografia;

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
public interface MonografiaMapper {

    @Mapping(target = "matricula", ignore = true)
    MonografiaDTO updateMonografiaDTO(Monografia monografia,
            @MappingTarget MonografiaDTO monografiaDTO);

    @AfterMapping
    default void afterUpdateMonografiaDTO(Monografia monografia,
            @MappingTarget MonografiaDTO monografiaDTO) {
        monografiaDTO.setMatricula(monografia.getMatricula() == null ? null : monografia.getMatricula().getCodMatricula());
    }

    @Mapping(target = "idMonografia", ignore = true)
    @Mapping(target = "matricula", ignore = true)
    Monografia updateMonografia(MonografiaDTO monografiaDTO, @MappingTarget Monografia monografia,
            @Context MatriculaRepository matriculaRepository);

    @AfterMapping
    default void afterUpdateMonografia(MonografiaDTO monografiaDTO,
            @MappingTarget Monografia monografia,
            @Context MatriculaRepository matriculaRepository) {
        final Matricula matricula = monografiaDTO.getMatricula() == null ? null : matriculaRepository.findById(monografiaDTO.getMatricula())
                .orElseThrow(() -> new NotFoundException("matricula not found"));
        monografia.setMatricula(matricula);
    }

}
