package uap.edu.bo.escuela_tecnica.programacion;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModulo;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModuloRepository;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProgramacionMapper {

    @Mapping(target = "cronogramaModulo", ignore = true)
    @Mapping(target = "matricula", ignore = true)
    ProgramacionDTO updateProgramacionDTO(Programacion programacion,
            @MappingTarget ProgramacionDTO programacionDTO);

    @AfterMapping
    default void afterUpdateProgramacionDTO(Programacion programacion,
            @MappingTarget ProgramacionDTO programacionDTO) {
        programacionDTO.setCronogramaModulo(programacion.getCronogramaModulo() == null ? null : programacion.getCronogramaModulo().getIdCronogramaMod());
        programacionDTO.setMatricula(programacion.getMatricula() == null ? null : programacion.getMatricula().getCodMatricula());
    }

    @Mapping(target = "idProgramacion", ignore = true)
    @Mapping(target = "cronogramaModulo", ignore = true)
    @Mapping(target = "matricula", ignore = true)
    Programacion updateProgramacion(ProgramacionDTO programacionDTO,
            @MappingTarget Programacion programacion,
            @Context CronogramaModuloRepository cronogramaModuloRepository,
            @Context MatriculaRepository matriculaRepository);

    @AfterMapping
    default void afterUpdateProgramacion(ProgramacionDTO programacionDTO,
            @MappingTarget Programacion programacion,
            @Context CronogramaModuloRepository cronogramaModuloRepository,
            @Context MatriculaRepository matriculaRepository) {
        final CronogramaModulo cronogramaModulo = programacionDTO.getCronogramaModulo() == null ? null : cronogramaModuloRepository.findById(programacionDTO.getCronogramaModulo())
                .orElseThrow(() -> new NotFoundException("cronogramaModulo not found"));
        programacion.setCronogramaModulo(cronogramaModulo);
        final Matricula matricula = programacionDTO.getMatricula() == null ? null : matriculaRepository.findById(programacionDTO.getMatricula())
                .orElseThrow(() -> new NotFoundException("matricula not found"));
        programacion.setMatricula(matricula);
    }

}
