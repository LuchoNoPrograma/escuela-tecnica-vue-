package uap.edu.bo.escuela_tecnica.comprobante_pago;

import java.util.HashSet;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.detalle_pago.DetallePago;
import uap.edu.bo.escuela_tecnica.detalle_pago.DetallePagoRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ComprobantePagoMapper {

    @Mapping(target = "listaDetallesPagos", ignore = true)
    ComprobantePagoDTO updateComprobantePagoDTO(ComprobantePago comprobantePago,
            @MappingTarget ComprobantePagoDTO comprobantePagoDTO);

    @AfterMapping
    default void afterUpdateComprobantePagoDTO(ComprobantePago comprobantePago,
            @MappingTarget ComprobantePagoDTO comprobantePagoDTO) {
        comprobantePagoDTO.setListaDetallesPagos(comprobantePago.getListaDetallesPagos().stream()
                .map(detallePago -> detallePago.getIdDetallePago())
                .toList());
    }

    @Mapping(target = "idComprobante", ignore = true)
    @Mapping(target = "listaDetallesPagos", ignore = true)
    ComprobantePago updateComprobantePago(ComprobantePagoDTO comprobantePagoDTO,
            @MappingTarget ComprobantePago comprobantePago,
            @Context DetallePagoRepository detallePagoRepository);

    @AfterMapping
    default void afterUpdateComprobantePago(ComprobantePagoDTO comprobantePagoDTO,
            @MappingTarget ComprobantePago comprobantePago,
            @Context DetallePagoRepository detallePagoRepository) {
        final List<DetallePago> listaDetallesPagos = detallePagoRepository.findAllById(
                comprobantePagoDTO.getListaDetallesPagos() == null ? List.of() : comprobantePagoDTO.getListaDetallesPagos());
        if (listaDetallesPagos.size() != (comprobantePagoDTO.getListaDetallesPagos() == null ? 0 : comprobantePagoDTO.getListaDetallesPagos().size())) {
            throw new NotFoundException("one of listaDetallesPagos not found");
        }
        comprobantePago.setListaDetallesPagos(new HashSet<>(listaDetallesPagos));
    }

}
