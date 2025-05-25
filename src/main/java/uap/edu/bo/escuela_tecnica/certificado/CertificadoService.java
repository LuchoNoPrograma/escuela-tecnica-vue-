package uap.edu.bo.escuela_tecnica.certificado;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uap.edu.bo.escuela_tecnica.administrativo.AdministrativoRepository;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Service
public class CertificadoService {

    private final CertificadoRepository certificadoRepository;
    private final AdministrativoRepository administrativoRepository;
    private final MatriculaRepository matriculaRepository;
    private final CertificadoMapper certificadoMapper;

    public CertificadoService(final CertificadoRepository certificadoRepository,
            final AdministrativoRepository administrativoRepository,
            final MatriculaRepository matriculaRepository,
            final CertificadoMapper certificadoMapper) {
        this.certificadoRepository = certificadoRepository;
        this.administrativoRepository = administrativoRepository;
        this.matriculaRepository = matriculaRepository;
        this.certificadoMapper = certificadoMapper;
    }

    public List<CertificadoDTO> findAll() {
        final List<Certificado> certificadoes = certificadoRepository.findAll(Sort.by("idCertificado"));
        return certificadoes.stream()
                .map(certificado -> certificadoMapper.updateCertificadoDTO(certificado, new CertificadoDTO()))
                .toList();
    }

    public CertificadoDTO get(final Long idCertificado) {
        return certificadoRepository.findById(idCertificado)
                .map(certificado -> certificadoMapper.updateCertificadoDTO(certificado, new CertificadoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CertificadoDTO certificadoDTO) {
        final Certificado certificado = new Certificado();
        certificadoMapper.updateCertificado(certificadoDTO, certificado, administrativoRepository, matriculaRepository);
        return certificadoRepository.save(certificado).getIdCertificado();
    }

    public void update(final Long idCertificado, final CertificadoDTO certificadoDTO) {
        final Certificado certificado = certificadoRepository.findById(idCertificado)
                .orElseThrow(NotFoundException::new);
        certificadoMapper.updateCertificado(certificadoDTO, certificado, administrativoRepository, matriculaRepository);
        certificadoRepository.save(certificado);
    }

    public void delete(final Long idCertificado) {
        certificadoRepository.deleteById(idCertificado);
    }

}
