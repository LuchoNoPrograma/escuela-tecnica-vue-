package uap.edu.bo.escuela_tecnica.modalidad;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ModalidadDTO {

    private Long idModalidad;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    @Size(max = 50)
    private String nombre;

}
