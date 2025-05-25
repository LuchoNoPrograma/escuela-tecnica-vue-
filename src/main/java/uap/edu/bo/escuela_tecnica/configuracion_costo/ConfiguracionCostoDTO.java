package uap.edu.bo.escuela_tecnica.configuracion_costo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ConfiguracionCostoDTO {

    private Long idConfiguracionCosto;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "27.08")
    private BigDecimal montoConfig;

    @NotNull
    private LocalDate fecInicioVig;

    private LocalDate fecFinVig;

    @NotNull
    private Boolean estConfig;

    @NotNull
    @Size(max = 55)
    private String concepto;

    @NotNull
    private Long programa;

}
