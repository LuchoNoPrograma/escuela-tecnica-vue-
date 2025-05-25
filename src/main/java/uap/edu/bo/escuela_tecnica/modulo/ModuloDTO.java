package uap.edu.bo.escuela_tecnica.modulo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ModuloDTO {

    private Long idModulo;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Size(max = 100)
    private String nombreMod;

    private String competencia;

}
