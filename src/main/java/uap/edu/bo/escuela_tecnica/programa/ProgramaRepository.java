package uap.edu.bo.escuela_tecnica.programa;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.categoria.Categoria;
import uap.edu.bo.escuela_tecnica.modalidad.Modalidad;


public interface ProgramaRepository extends JpaRepository<Programa, Long> {

    Programa findFirstByModalidad(Modalidad modalidad);

    Programa findFirstByCategoria(Categoria categoria);

}
