package uap.edu.bo.escuela_tecnica.observacion_monografia;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ObservacionMonografiaDTO {

    private Long idObservacionMonografia;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    private String descripcion;

    @NotNull
    private Long revisionMonografia;

}
