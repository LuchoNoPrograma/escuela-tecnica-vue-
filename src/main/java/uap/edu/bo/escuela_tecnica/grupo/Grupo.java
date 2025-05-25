package uap.edu.bo.escuela_tecnica.grupo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModulo;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;
import uap.edu.bo.escuela_tecnica.programa.Programa;
import uap.edu.bo.escuela_tecnica.version.Version;


@Entity
@Getter
@Setter
public class Grupo extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrupo;

    @Column(nullable = false, length = 100)
    private String nombreGrupo;

    @Column(nullable = false, length = 35)
    private String estGrupo;

    @OneToMany(mappedBy = "grupo")
    private Set<CronogramaModulo> listaCronogramas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_version")
    private Version version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_programa", nullable = false)
    private Programa programa;

    @OneToMany(mappedBy = "grupo")
    private Set<Matricula> listaMatriculas;

}
