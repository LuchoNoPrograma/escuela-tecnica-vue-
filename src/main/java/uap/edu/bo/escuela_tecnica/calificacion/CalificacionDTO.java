package uap.edu.bo.escuela_tecnica.calificacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CalificacionDTO {

    private Long idCalificacion;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @Digits(integer = 7, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "78.08")
    private BigDecimal nota;

    @NotNull
    private Long criterioEval;

    @NotNull
    private Long programacion;

}
