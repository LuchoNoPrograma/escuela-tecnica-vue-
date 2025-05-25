package uap.edu.bo.escuela_tecnica.criterio_eval;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CriterioEvalDTO {

    private Long idCriterioEval;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Size(max = 25)
    private String nombreCrit;

    @Size(max = 155)
    private String descripcion;

    @NotNull
    private Integer ponderacion;

    @NotNull
    private Integer orden;

    @NotNull
    private Long cronogramaMod;

}
