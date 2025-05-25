package uap.edu.bo.escuela_tecnica.nivel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NivelDTO {

    private Long idNivel;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Size(max = 100)
    private String nombreNivel;

}
