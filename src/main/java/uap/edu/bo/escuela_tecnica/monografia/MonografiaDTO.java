package uap.edu.bo.escuela_tecnica.monografia;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MonografiaDTO {

    private Long idMonografia;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Size(max = 255)
    private String titulo;

    @NotNull
    @Size(max = 35)
    private String estMonografia;

    @NotNull
    private Integer notaFinal;

    private Long matricula;

}
