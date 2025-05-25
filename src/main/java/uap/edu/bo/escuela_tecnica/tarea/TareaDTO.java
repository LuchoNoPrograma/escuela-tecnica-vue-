package uap.edu.bo.escuela_tecnica.tarea;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TareaDTO {

    private Long idTarea;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Size(max = 50)
    private String nombreTarea;

    private String descripTarea;

    @NotNull
    private Boolean estTarea;

    @NotNull
    private Long agrupa;

}
