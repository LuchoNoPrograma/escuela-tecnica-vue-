package uap.edu.bo.escuela_tecnica.revision_monografia;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RevisionMonografiaDTO {

    private Long idRevisionMonografia;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    private LocalDateTime fecHoraDesignacion;

    private Boolean esAprobadorFinal;

    private LocalDateTime fecHoraRevision;

    @NotNull
    @Size(max = 35)
    private String estRevisionMoografia;

    @NotNull
    private Long monografia;

    private Long administrativo;

    private Long docente;

}
