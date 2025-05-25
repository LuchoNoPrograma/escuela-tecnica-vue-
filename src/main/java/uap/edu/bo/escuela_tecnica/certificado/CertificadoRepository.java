package uap.edu.bo.escuela_tecnica.certificado;

import org.springframework.data.jpa.repository.JpaRepository;
import uap.edu.bo.escuela_tecnica.administrativo.Administrativo;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;


public interface CertificadoRepository extends JpaRepository<Certificado, Long> {

    Certificado findFirstByAdministrativo(Administrativo administrativo);

    Certificado findFirstByMatricula(Matricula matricula);

}
