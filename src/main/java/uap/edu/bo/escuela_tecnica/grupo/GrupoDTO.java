package uap.edu.bo.escuela_tecnica.grupo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GrupoDTO {

    private Long idGrupo;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @NotNull
    @Size(max = 100)
    private String nombreGrupo;

    @NotNull
    @Size(max = 35)
    private String estGrupo;

    private Integer version;

    @NotNull
    private Long programa;

}
