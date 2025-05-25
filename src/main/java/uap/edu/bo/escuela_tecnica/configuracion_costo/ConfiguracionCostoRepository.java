package uap.edu.bo.escuela_tecnica.configuracion_costo;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.programa.Programa;


public interface ConfiguracionCostoRepository extends JpaRepository<ConfiguracionCosto, Long> {

    ConfiguracionCosto findFirstByPrograma(Programa programa);

}
