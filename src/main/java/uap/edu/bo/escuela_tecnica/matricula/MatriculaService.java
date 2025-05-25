package uap.edu.bo.escuela_tecnica.matricula;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.certificado.Certificado;
import uap.edu.bo.escuela_tecnica.certificado.CertificadoRepository;
import uap.edu.bo.escuela_tecnica.grupo.GrupoRepository;
import uap.edu.bo.escuela_tecnica.monografia.Monografia;
import uap.edu.bo.escuela_tecnica.monografia.MonografiaRepository;
import uap.edu.bo.escuela_tecnica.persona.PersonaRepository;
import uap.edu.bo.escuela_tecnica.plan_pago.PlanPago;
import uap.edu.bo.escuela_tecnica.plan_pago.PlanPagoRepository;
import uap.edu.bo.escuela_tecnica.programacion.Programacion;
import uap.edu.bo.escuela_tecnica.programacion.ProgramacionRepository;
import uap.edu.bo.escuela_tecnica.titulacion.Titulacion;
import uap.edu.bo.escuela_tecnica.titulacion.TitulacionRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final GrupoRepository grupoRepository;
    private final PersonaRepository personaRepository;
    private final MatriculaMapper matriculaMapper;
    private final CertificadoRepository certificadoRepository;
    private final MonografiaRepository monografiaRepository;
    private final PlanPagoRepository planPagoRepository;
    private final ProgramacionRepository programacionRepository;
    private final TitulacionRepository titulacionRepository;

    public MatriculaService(final MatriculaRepository matriculaRepository,
            final GrupoRepository grupoRepository, final PersonaRepository personaRepository,
            final MatriculaMapper matriculaMapper,
            final CertificadoRepository certificadoRepository,
            final MonografiaRepository monografiaRepository,
            final PlanPagoRepository planPagoRepository,
            final ProgramacionRepository programacionRepository,
            final TitulacionRepository titulacionRepository) {
        this.matriculaRepository = matriculaRepository;
        this.grupoRepository = grupoRepository;
        this.personaRepository = personaRepository;
        this.matriculaMapper = matriculaMapper;
        this.certificadoRepository = certificadoRepository;
        this.monografiaRepository = monografiaRepository;
        this.planPagoRepository = planPagoRepository;
        this.programacionRepository = programacionRepository;
        this.titulacionRepository = titulacionRepository;
    }

    public List<MatriculaDTO> findAll() {
        final List<Matricula> matriculas = matriculaRepository.findAll(Sort.by("codMatricula"));
        return matriculas.stream()
                .map(matricula -> matriculaMapper.updateMatriculaDTO(matricula, new MatriculaDTO()))
                .toList();
    }

    public MatriculaDTO get(final Long codMatricula) {
        return matriculaRepository.findById(codMatricula)
                .map(matricula -> matriculaMapper.updateMatriculaDTO(matricula, new MatriculaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MatriculaDTO matriculaDTO) {
        final Matricula matricula = new Matricula();
        matriculaMapper.updateMatricula(matriculaDTO, matricula, grupoRepository, personaRepository);
        return matriculaRepository.save(matricula).getCodMatricula();
    }

    public void update(final Long codMatricula, final MatriculaDTO matriculaDTO) {
        final Matricula matricula = matriculaRepository.findById(codMatricula)
                .orElseThrow(NotFoundException::new);
        matriculaMapper.updateMatricula(matriculaDTO, matricula, grupoRepository, personaRepository);
        matriculaRepository.save(matricula);
    }

    public void delete(final Long codMatricula) {
        matriculaRepository.deleteById(codMatricula);
    }

    public ReferencedWarning getReferencedWarning(final Long codMatricula) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Matricula matricula = matriculaRepository.findById(codMatricula)
                .orElseThrow(NotFoundException::new);
        final Certificado matriculaCertificado = certificadoRepository.findFirstByMatricula(matricula);
        if (matriculaCertificado != null) {
            referencedWarning.setKey("matricula.certificado.matricula.referenced");
            referencedWarning.addParam(matriculaCertificado.getIdCertificado());
            return referencedWarning;
        }
        final Monografia matriculaMonografia = monografiaRepository.findFirstByMatricula(matricula);
        if (matriculaMonografia != null) {
            referencedWarning.setKey("matricula.monografia.matricula.referenced");
            referencedWarning.addParam(matriculaMonografia.getIdMonografia());
            return referencedWarning;
        }
        final PlanPago matriculaPlanPago = planPagoRepository.findFirstByMatricula(matricula);
        if (matriculaPlanPago != null) {
            referencedWarning.setKey("matricula.planPago.matricula.referenced");
            referencedWarning.addParam(matriculaPlanPago.getIdPlanPago());
            return referencedWarning;
        }
        final Programacion matriculaProgramacion = programacionRepository.findFirstByMatricula(matricula);
        if (matriculaProgramacion != null) {
            referencedWarning.setKey("matricula.programacion.matricula.referenced");
            referencedWarning.addParam(matriculaProgramacion.getIdProgramacion());
            return referencedWarning;
        }
        final Titulacion matriculaTitulacion = titulacionRepository.findFirstByMatricula(matricula);
        if (matriculaTitulacion != null) {
            referencedWarning.setKey("matricula.titulacion.matricula.referenced");
            referencedWarning.addParam(matriculaTitulacion.getIdTitulacion());
            return referencedWarning;
        }
        return null;
    }

}
