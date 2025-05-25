package uap.edu.bo.escuela_tecnica.preinscripcion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PreinscripcionRepository extends JpaRepository<Preinscripcion, Long> {

    Page<Preinscripcion> findAllByIdPreinscripcion(Long idPreinscripcion, Pageable pageable);

}
