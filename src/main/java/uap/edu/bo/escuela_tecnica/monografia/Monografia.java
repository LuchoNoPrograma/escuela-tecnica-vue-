package uap.edu.bo.escuela_tecnica.monografia;

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
import uap.edu.bo.escuela_tecnica.matricula.Matricula;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografia;


@Entity
@Getter
@Setter
public class Monografia extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMonografia;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 35)
    private String estMonografia;

    @Column(nullable = false)
    private Integer notaFinal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_cod_matricula")
    private Matricula matricula;

    @OneToMany(mappedBy = "monografia")
    private Set<RevisionMonografia> listaRevisiones;

}
