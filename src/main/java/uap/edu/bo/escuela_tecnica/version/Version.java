package uap.edu.bo.escuela_tecnica.version;

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
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.programa.Programa;


@Entity
@Getter
@Setter
public class Version extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVersion;

    @Column(nullable = false, length = 15)
    private String codVersion;

    @Column(nullable = false)
    private LocalDate fecInicioVigencia;

    @Column
    private LocalDate fecFinVigencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_programa", nullable = false)
    private Programa programa;

}
