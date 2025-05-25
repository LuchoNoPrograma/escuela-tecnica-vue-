package uap.edu.bo.escuela_tecnica.titulacion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;


@Entity
@Getter
@Setter
public class Titulacion extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTitulacion;

    @Column(nullable = false)
    private LocalDateTime fecEmision;

    @Column(nullable = false, length = 25)
    private String codTitulo;

    @Column(nullable = false, columnDefinition = "text")
    private String urlDoc;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_cod_matricula", nullable = false, unique = true)
    private Matricula matricula;

}
