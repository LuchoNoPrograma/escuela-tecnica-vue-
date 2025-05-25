package uap.edu.bo.escuela_tecnica.usuario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    private Long idUsuario;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @Size(max = 50)
    private String nombreUsuario;

    @Size(max = 255)
    private String contrasenaHash;

    @Size(max = 35)
    private String estUsuario;

    private List<Long> listaTareasAsignadas;

    @NotNull
    @UsuarioPersonaUnique
    private Long persona;

}
