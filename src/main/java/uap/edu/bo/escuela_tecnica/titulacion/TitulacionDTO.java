package uap.edu.bo.escuela_tecnica.titulacion;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TitulacionDTO {

    private Long idTitulacion;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    private LocalDateTime fecEmision;

    @NotNull
    @Size(max = 25)
    private String codTitulo;

    @NotNull
    private String urlDoc;

    @NotNull
    @TitulacionMatriculaUnique
    private Long matricula;

}
