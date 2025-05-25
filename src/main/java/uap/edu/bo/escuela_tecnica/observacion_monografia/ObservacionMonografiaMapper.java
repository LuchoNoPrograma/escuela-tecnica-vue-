package uap.edu.bo.escuela_tecnica.observacion_monografia;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografia;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografiaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ObservacionMonografiaMapper {

    @Mapping(target = "revisionMonografia", ignore = true)
    ObservacionMonografiaDTO updateObservacionMonografiaDTO(
            ObservacionMonografia observacionMonografia,
            @MappingTarget ObservacionMonografiaDTO observacionMonografiaDTO);

    @AfterMapping
    default void afterUpdateObservacionMonografiaDTO(ObservacionMonografia observacionMonografia,
            @MappingTarget ObservacionMonografiaDTO observacionMonografiaDTO) {
        observacionMonografiaDTO.setRevisionMonografia(observacionMonografia.getRevisionMonografia() == null ? null : observacionMonografia.getRevisionMonografia().getIdRevisionMonografia());
    }

    @Mapping(target = "idObservacionMonografia", ignore = true)
    @Mapping(target = "revisionMonografia", ignore = true)
    ObservacionMonografia updateObservacionMonografia(
            ObservacionMonografiaDTO observacionMonografiaDTO,
            @MappingTarget ObservacionMonografia observacionMonografia,
            @Context RevisionMonografiaRepository revisionMonografiaRepository);

    @AfterMapping
    default void afterUpdateObservacionMonografia(ObservacionMonografiaDTO observacionMonografiaDTO,
            @MappingTarget ObservacionMonografia observacionMonografia,
            @Context RevisionMonografiaRepository revisionMonografiaRepository) {
        final RevisionMonografia revisionMonografia = observacionMonografiaDTO.getRevisionMonografia() == null ? null : revisionMonografiaRepository.findById(observacionMonografiaDTO.getRevisionMonografia())
                .orElseThrow(() -> new NotFoundException("revisionMonografia not found"));
        observacionMonografia.setRevisionMonografia(revisionMonografia);
    }

}
