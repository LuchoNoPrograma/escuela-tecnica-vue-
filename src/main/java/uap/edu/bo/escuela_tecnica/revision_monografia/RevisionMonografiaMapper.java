package uap.edu.bo.escuela_tecnica.revision_monografia;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.administrativo.Administrativo;
import uap.edu.bo.escuela_tecnica.administrativo.AdministrativoRepository;
import uap.edu.bo.escuela_tecnica.docente.Docente;
import uap.edu.bo.escuela_tecnica.docente.DocenteRepository;
import uap.edu.bo.escuela_tecnica.monografia.Monografia;
import uap.edu.bo.escuela_tecnica.monografia.MonografiaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RevisionMonografiaMapper {

    @Mapping(target = "monografia", ignore = true)
    @Mapping(target = "administrativo", ignore = true)
    @Mapping(target = "docente", ignore = true)
    RevisionMonografiaDTO updateRevisionMonografiaDTO(RevisionMonografia revisionMonografia,
            @MappingTarget RevisionMonografiaDTO revisionMonografiaDTO);

    @AfterMapping
    default void afterUpdateRevisionMonografiaDTO(RevisionMonografia revisionMonografia,
            @MappingTarget RevisionMonografiaDTO revisionMonografiaDTO) {
        revisionMonografiaDTO.setMonografia(revisionMonografia.getMonografia() == null ? null : revisionMonografia.getMonografia().getIdMonografia());
        revisionMonografiaDTO.setAdministrativo(revisionMonografia.getAdministrativo() == null ? null : revisionMonografia.getAdministrativo().getIdAdministrativo());
        revisionMonografiaDTO.setDocente(revisionMonografia.getDocente() == null ? null : revisionMonografia.getDocente().getIdDocente());
    }

    @Mapping(target = "idRevisionMonografia", ignore = true)
    @Mapping(target = "monografia", ignore = true)
    @Mapping(target = "administrativo", ignore = true)
    @Mapping(target = "docente", ignore = true)
    RevisionMonografia updateRevisionMonografia(RevisionMonografiaDTO revisionMonografiaDTO,
            @MappingTarget RevisionMonografia revisionMonografia,
            @Context MonografiaRepository monografiaRepository,
            @Context AdministrativoRepository administrativoRepository,
            @Context DocenteRepository docenteRepository);

    @AfterMapping
    default void afterUpdateRevisionMonografia(RevisionMonografiaDTO revisionMonografiaDTO,
            @MappingTarget RevisionMonografia revisionMonografia,
            @Context MonografiaRepository monografiaRepository,
            @Context AdministrativoRepository administrativoRepository,
            @Context DocenteRepository docenteRepository) {
        final Monografia monografia = revisionMonografiaDTO.getMonografia() == null ? null : monografiaRepository.findById(revisionMonografiaDTO.getMonografia())
                .orElseThrow(() -> new NotFoundException("monografia not found"));
        revisionMonografia.setMonografia(monografia);
        final Administrativo administrativo = revisionMonografiaDTO.getAdministrativo() == null ? null : administrativoRepository.findById(revisionMonografiaDTO.getAdministrativo())
                .orElseThrow(() -> new NotFoundException("administrativo not found"));
        revisionMonografia.setAdministrativo(administrativo);
        final Docente docente = revisionMonografiaDTO.getDocente() == null ? null : docenteRepository.findById(revisionMonografiaDTO.getDocente())
                .orElseThrow(() -> new NotFoundException("docente not found"));
        revisionMonografia.setDocente(docente);
    }

}
