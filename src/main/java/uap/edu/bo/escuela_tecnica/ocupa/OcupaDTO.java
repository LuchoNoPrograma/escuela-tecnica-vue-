package uap.edu.bo.escuela_tecnica.ocupa;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OcupaDTO {

    @Size(max = 25)
    @OcupaEstOcupaValid
    private String estOcupa;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    private Long rol;

    @NotNull
    private Long usuario;

}
