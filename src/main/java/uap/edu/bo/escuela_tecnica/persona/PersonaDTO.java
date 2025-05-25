package uap.edu.bo.escuela_tecnica.persona;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PersonaDTO {

    private Long idPersona;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Size(max = 35)
    private String nombre;

    @NotNull
    @Size(max = 55)
    private String apPaterno;

    @Size(max = 55)
    private String apMaterno;

    @NotNull
    @Size(max = 20)
    private String ci;

    @NotNull
    @Size(max = 20)
    private String nroCelular;

    @Size(max = 55)
    private String correo;

}
