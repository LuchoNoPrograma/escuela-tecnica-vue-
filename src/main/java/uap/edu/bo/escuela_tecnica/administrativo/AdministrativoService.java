package uap.edu.bo.escuela_tecnica.administrativo;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.certificado.Certificado;
import uap.edu.bo.escuela_tecnica.certificado.CertificadoRepository;
import uap.edu.bo.escuela_tecnica.persona.PersonaRepository;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografia;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografiaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@Service
public class AdministrativoService {

    private final AdministrativoRepository administrativoRepository;
    private final PersonaRepository personaRepository;
    private final AdministrativoMapper administrativoMapper;
    private final CertificadoRepository certificadoRepository;
    private final RevisionMonografiaRepository revisionMonografiaRepository;

    public AdministrativoService(final AdministrativoRepository administrativoRepository,
            final PersonaRepository personaRepository,
            final AdministrativoMapper administrativoMapper,
            final CertificadoRepository certificadoRepository,
            final RevisionMonografiaRepository revisionMonografiaRepository) {
        this.administrativoRepository = administrativoRepository;
        this.personaRepository = personaRepository;
        this.administrativoMapper = administrativoMapper;
        this.certificadoRepository = certificadoRepository;
        this.revisionMonografiaRepository = revisionMonografiaRepository;
    }

    public List<AdministrativoDTO> findAll() {
        final List<Administrativo> administrativoes = administrativoRepository.findAll(Sort.by("idAdministrativo"));
        return administrativoes.stream()
                .map(administrativo -> administrativoMapper.updateAdministrativoDTO(administrativo, new AdministrativoDTO()))
                .toList();
    }

    public AdministrativoDTO get(final Long idAdministrativo) {
        return administrativoRepository.findById(idAdministrativo)
                .map(administrativo -> administrativoMapper.updateAdministrativoDTO(administrativo, new AdministrativoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AdministrativoDTO administrativoDTO) {
        final Administrativo administrativo = new Administrativo();
        administrativoMapper.updateAdministrativo(administrativoDTO, administrativo, personaRepository);
        return administrativoRepository.save(administrativo).getIdAdministrativo();
    }

    public void update(final Long idAdministrativo, final AdministrativoDTO administrativoDTO) {
        final Administrativo administrativo = administrativoRepository.findById(idAdministrativo)
                .orElseThrow(NotFoundException::new);
        administrativoMapper.updateAdministrativo(administrativoDTO, administrativo, personaRepository);
        administrativoRepository.save(administrativo);
    }

    public void delete(final Long idAdministrativo) {
        administrativoRepository.deleteById(idAdministrativo);
    }

    public ReferencedWarning getReferencedWarning(final Long idAdministrativo) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Administrativo administrativo = administrativoRepository.findById(idAdministrativo)
                .orElseThrow(NotFoundException::new);
        final Certificado administrativoCertificado = certificadoRepository.findFirstByAdministrativo(administrativo);
        if (administrativoCertificado != null) {
            referencedWarning.setKey("administrativo.certificado.administrativo.referenced");
            referencedWarning.addParam(administrativoCertificado.getIdCertificado());
            return referencedWarning;
        }
        final RevisionMonografia administrativoRevisionMonografia = revisionMonografiaRepository.findFirstByAdministrativo(administrativo);
        if (administrativoRevisionMonografia != null) {
            referencedWarning.setKey("administrativo.revisionMonografia.administrativo.referenced");
            referencedWarning.addParam(administrativoRevisionMonografia.getIdRevisionMonografia());
            return referencedWarning;
        }
        return null;
    }

}
