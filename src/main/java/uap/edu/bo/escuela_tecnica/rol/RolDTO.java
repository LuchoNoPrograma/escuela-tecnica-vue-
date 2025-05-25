package uap.edu.bo.escuela_tecnica.rol;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RolDTO {

    private Long idPermiso;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Size(max = 50)
    private String nombre;

    @NotNull
    @Size(max = 35)
    private String estCargo;

    private String descripcionCargo;

}
