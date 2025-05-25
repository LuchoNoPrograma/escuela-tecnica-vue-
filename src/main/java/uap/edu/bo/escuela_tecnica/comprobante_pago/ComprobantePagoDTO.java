package uap.edu.bo.escuela_tecnica.comprobante_pago;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ComprobantePagoDTO {

    private Long idComprobante;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Size(max = 25)
    private String codComprobante;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "11.08")
    private BigDecimal monto;

    @NotNull
    private LocalDateTime fecComprobante;

    @NotNull
    @Size(max = 35)
    private String tipoDeposito;

    private List<Long> listaDetallesPagos;

}
