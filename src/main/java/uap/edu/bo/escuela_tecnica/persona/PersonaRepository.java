package uap.edu.bo.escuela_tecnica.persona;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByCi(String cedula);
}
