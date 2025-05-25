package uap.edu.bo.escuela_tecnica.categoria;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategoriaDTO {

    private Long idCategoria;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @Size(max = 100)
    private String nombreCat;

}
