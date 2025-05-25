package uap.edu.bo.escuela_tecnica.docente;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DocenteDTO {

    private Long idDocente;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    private Integer nroResolucion;

    @NotNull
    private LocalDate fecInicioContrato;

    private LocalDate fecFinContrato;

    @NotNull
    private Long persona;

}
