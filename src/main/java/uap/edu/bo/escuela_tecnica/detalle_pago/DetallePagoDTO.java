package uap.edu.bo.escuela_tecnica.detalle_pago;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DetallePagoDTO {

    private Long idDetallePago;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @Digits(integer = 12, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "74.08")
    private BigDecimal montoPagadoDetalle;

    @NotNull
    private Long planPago;

}
