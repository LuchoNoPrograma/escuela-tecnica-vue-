package uap.edu.bo.escuela_tecnica.preinscripcion;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PreinscripcionDTO {

    private Long idPreinscripcion;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Size(max = 55)
    private String nombres;

    @NotNull
    @Size(max = 35)
    private String paterno;

    @Size(max = 35)
    private String materno;

    @Size(max = 255)
    private String ci;

    @Size(max = 15)
    private String celular;

}
