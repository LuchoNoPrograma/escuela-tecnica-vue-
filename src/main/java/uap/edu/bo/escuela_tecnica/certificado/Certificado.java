package uap.edu.bo.escuela_tecnica.certificado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.administrativo.Administrativo;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;


@Entity
@Getter
@Setter
public class Certificado extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCertificado;

    @Column
    private LocalDate fechaEmision;

    @Column(length = 55)
    private String estCertificado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_administrativo", nullable = false)
    private Administrativo administrativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_cod_matricula")
    private Matricula matricula;

}
