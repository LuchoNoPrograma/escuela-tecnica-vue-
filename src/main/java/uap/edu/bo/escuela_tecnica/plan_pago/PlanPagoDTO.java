package uap.edu.bo.escuela_tecnica.plan_pago;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlanPagoDTO {

    private Long idPlanPago;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "73.08")
    private BigDecimal deudaTotal;

    @Digits(integer = 12, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "98.08")
    private BigDecimal pagadoTotal;

    @NotNull
    @Size(max = 35)
    private String estPlanPago;

    @NotNull
    @Size(max = 155)
    private String concepto;

    @NotNull
    private Long matricula;

}
