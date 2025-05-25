package uap.edu.bo.escuela_tecnica.cronograma_modulo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CronogramaModuloDTO {

    private Long idCronogramaMod;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    private LocalDate fecInicio;

    private LocalDate fecFin;

    @NotNull
    @Size(max = 35)
    private String estado;

    @NotNull
    private Long planEstudioDetalle;

    @NotNull
    private Long grupo;

    private Long docente;

}
