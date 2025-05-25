package uap.edu.bo.escuela_tecnica.programa;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.categoria.Categoria;
import uap.edu.bo.escuela_tecnica.categoria.CategoriaRepository;
import uap.edu.bo.escuela_tecnica.modalidad.Modalidad;
import uap.edu.bo.escuela_tecnica.modalidad.ModalidadRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProgramaMapper {

    @Mapping(target = "modalidad", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    ProgramaDTO updateProgramaDTO(Programa programa, @MappingTarget ProgramaDTO programaDTO);

    @AfterMapping
    default void afterUpdateProgramaDTO(Programa programa, @MappingTarget ProgramaDTO programaDTO) {
        programaDTO.setModalidad(programa.getModalidad() == null ? null : programa.getModalidad().getIdModalidad());
        programaDTO.setCategoria(programa.getCategoria() == null ? null : programa.getCategoria().getIdCategoria());
    }

    @Mapping(target = "idPrograma", ignore = true)
    @Mapping(target = "modalidad", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Programa updatePrograma(ProgramaDTO programaDTO, @MappingTarget Programa programa,
            @Context ModalidadRepository modalidadRepository,
            @Context CategoriaRepository categoriaRepository);

    @AfterMapping
    default void afterUpdatePrograma(ProgramaDTO programaDTO, @MappingTarget Programa programa,
            @Context ModalidadRepository modalidadRepository,
            @Context CategoriaRepository categoriaRepository) {
        final Modalidad modalidad = programaDTO.getModalidad() == null ? null : modalidadRepository.findById(programaDTO.getModalidad())
                .orElseThrow(() -> new NotFoundException("modalidad not found"));
        programa.setModalidad(modalidad);
        final Categoria categoria = programaDTO.getCategoria() == null ? null : categoriaRepository.findById(programaDTO.getCategoria())
                .orElseThrow(() -> new NotFoundException("categoria not found"));
        programa.setCategoria(categoria);
    }

}
