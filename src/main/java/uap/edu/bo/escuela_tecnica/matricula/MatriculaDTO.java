package uap.edu.bo.escuela_tecnica.matricula;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MatriculaDTO {

    private Long codMatricula;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    private LocalDate fecMatriculacion;

    @NotNull
    @Size(max = 35)
    private String estMatricula;

    @NotNull
    private Long grupo;

    @NotNull
    private Long persona;

}
