package uap.edu.bo.escuela_tecnica.programa;

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
import uap.edu.bo.escuela_tecnica.categoria.Categoria;
import uap.edu.bo.escuela_tecnica.configuracion_costo.ConfiguracionCosto;
import uap.edu.bo.escuela_tecnica.grupo.Grupo;
import uap.edu.bo.escuela_tecnica.modalidad.Modalidad;
import uap.edu.bo.escuela_tecnica.version.Version;


@Entity
@Getter
@Setter
public class Programa extends Auditoria {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrograma;

    @Column(nullable = false, length = 100)
    private String nombrePrograma;

    @Column(nullable = false, length = 35)
    private String estPrograma;

    @OneToMany(mappedBy = "programa")
    private Set<ConfiguracionCosto> listaConfiguracionCostos;

    @OneToMany(mappedBy = "programa")
    private Set<Grupo> listaGrupos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_modalidad", nullable = false)
    private Modalidad modalidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_categoria", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "programa")
    private Set<Version> listaVersiones;

}
