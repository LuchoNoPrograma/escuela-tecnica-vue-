package uap.edu.bo.escuela_tecnica.programa;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProgramaDTO {

    private Long idPrograma;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Size(max = 100)
    private String nombrePrograma;

    @NotNull
    @Size(max = 35)
    private String estPrograma;

    @NotNull
    private Long modalidad;

    @NotNull
    private Long categoria;

}
