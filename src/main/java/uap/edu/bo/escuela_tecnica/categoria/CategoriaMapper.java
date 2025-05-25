package uap.edu.bo.escuela_tecnica.categoria;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoriaMapper {

    CategoriaDTO updateCategoriaDTO(Categoria categoria, @MappingTarget CategoriaDTO categoriaDTO);

    @Mapping(target = "idCategoria", ignore = true)
    Categoria updateCategoria(CategoriaDTO categoriaDTO, @MappingTarget Categoria categoria);

}
