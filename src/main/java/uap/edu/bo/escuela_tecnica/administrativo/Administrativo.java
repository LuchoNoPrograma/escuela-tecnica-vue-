package uap.edu.bo.escuela_tecnica.administrativo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.persona.Persona;


@Entity
@Getter
@Setter
public class Administrativo extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdministrativo;

    @Column
    private LocalDateTime fecIngreso;

    @Column
    private LocalDateTime fecSalida;

    @Column
    private Boolean habil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_persona", nullable = false)
    private Persona persona;

}
