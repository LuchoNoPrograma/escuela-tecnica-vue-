package uap.edu.bo.escuela_tecnica.ocupa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import uap.edu.bo.escuela_tecnica.auditoria.Auditoria;
import uap.edu.bo.escuela_tecnica.rol.Rol;
import uap.edu.bo.escuela_tecnica.usuario.Usuario;


@Entity
@Getter
@Setter
public class Ocupa extends Auditoria {

    @Id
    @Column(nullable = false, updatable = false, length = 25)
    private String estOcupa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_rol", nullable = false)
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_usuario", nullable = false)
    private Usuario usuario;

}
