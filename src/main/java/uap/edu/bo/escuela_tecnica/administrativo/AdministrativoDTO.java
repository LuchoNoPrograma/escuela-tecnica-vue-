package uap.edu.bo.escuela_tecnica.administrativo;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdministrativoDTO {

    private Long idAdministrativo;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    private LocalDateTime fecIngreso;

    private LocalDateTime fecSalida;

    private Boolean habil;

    @NotNull
    private Long persona;

}
