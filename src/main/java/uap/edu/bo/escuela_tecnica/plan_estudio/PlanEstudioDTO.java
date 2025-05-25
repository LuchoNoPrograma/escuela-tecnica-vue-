package uap.edu.bo.escuela_tecnica.plan_estudio;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlanEstudioDTO {

    private Long idPlanEstudio;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    private Integer anho;

    @NotNull
    private Boolean vigente;

    @NotNull
    private Long programa;

}
