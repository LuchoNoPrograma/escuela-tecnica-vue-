package uap.edu.bo.escuela_tecnica.categoria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.programa.Programa;


@Entity
@Getter
@Setter
public class Categoria extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    @Column(length = 100)
    private String nombreCat;

    @OneToMany(mappedBy = "categoria")
    private Set<Programa> categoriaProgramas;

}
