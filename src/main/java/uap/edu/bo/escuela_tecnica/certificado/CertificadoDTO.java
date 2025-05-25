package uap.edu.bo.escuela_tecnica.certificado;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CertificadoDTO {

    private Long idCertificado;

    @NotNull
    private Long idUsuReg;

    private Long idUsuMod;

    private LocalDate fechaEmision;

    @Size(max = 55)
    private String estCertificado;

    @NotNull
    private Long administrativo;

    private Long matricula;

}
