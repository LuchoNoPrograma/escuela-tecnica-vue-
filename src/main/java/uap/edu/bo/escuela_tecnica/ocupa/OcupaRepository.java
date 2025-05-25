package uap.edu.bo.escuela_tecnica.ocupa;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.rol.Rol;
import uap.edu.bo.escuela_tecnica.usuario.Usuario;


public interface OcupaRepository extends JpaRepository<Ocupa, String> {

    Ocupa findFirstByRol(Rol rol);

    Ocupa findFirstByUsuario(Usuario usuario);

    boolean existsByEstOcupaIgnoreCase(String estOcupa);

}
