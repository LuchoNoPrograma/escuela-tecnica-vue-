package uap.edu.bo.escuela_tecnica.plan_estudio_detalle;

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
public class PlanEstudioDetalleDTO {

    private Long idPlanEstudioDetalle;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    private Integer cargaHoraria;

    @NotNull
    @Digits(integer = 8, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "97.08")
    private BigDecimal creditos;

    @NotNull
    private Integer orden;

    @NotNull
    @Size(max = 10)
    private String sigla;

    @NotNull
    private Long nivel;

    @NotNull
    private Long modulo;

    @NotNull
    private Long planEstudio;

}
