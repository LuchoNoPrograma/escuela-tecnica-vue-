package uap.edu.bo.escuela_tecnica.programacion;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProgramacionDTO {

    private Long idProgramacion;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @Size(max = 255)
    private String observacion;

    @NotNull
    private LocalDateTime fecProgramacion;

    @NotNull
    @Size(max = 35)
    private String estCalificacion;

    @NotNull
    @Size(max = 35)
    private String tipoProgramacion;

    private Integer calificacionFinal;

    @NotNull
    private Long cronogramaModulo;

    @NotNull
    private Long matricula;

}
