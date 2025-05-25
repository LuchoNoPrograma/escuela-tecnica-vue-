package uap.edu.bo.escuela_tecnica.titulacion;

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
public interface TitulacionMapper {

    @Mapping(target = "matricula", ignore = true)
    TitulacionDTO updateTitulacionDTO(Titulacion titulacion,
            @MappingTarget TitulacionDTO titulacionDTO);

    @AfterMapping
    default void afterUpdateTitulacionDTO(Titulacion titulacion,
            @MappingTarget TitulacionDTO titulacionDTO) {
        titulacionDTO.setMatricula(titulacion.getMatricula() == null ? null : titulacion.getMatricula().getCodMatricula());
    }

    @Mapping(target = "idTitulacion", ignore = true)
    @Mapping(target = "matricula", ignore = true)
    Titulacion updateTitulacion(TitulacionDTO titulacionDTO, @MappingTarget Titulacion titulacion,
            @Context MatriculaRepository matriculaRepository);

    @AfterMapping
    default void afterUpdateTitulacion(TitulacionDTO titulacionDTO,
            @MappingTarget Titulacion titulacion,
            @Context MatriculaRepository matriculaRepository) {
        final Matricula matricula = titulacionDTO.getMatricula() == null ? null : matriculaRepository.findById(titulacionDTO.getMatricula())
                .orElseThrow(() -> new NotFoundException("matricula not found"));
        titulacion.setMatricula(matricula);
    }

}
