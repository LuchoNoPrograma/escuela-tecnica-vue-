package uap.edu.bo.escuela_tecnica.certificado;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uap.edu.bo.escuela_tecnica.administrativo.Administrativo;
import uap.edu.bo.escuela_tecnica.administrativo.AdministrativoRepository;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CertificadoMapper {

    @Mapping(target = "administrativo", ignore = true)
    @Mapping(target = "matricula", ignore = true)
    CertificadoDTO updateCertificadoDTO(Certificado certificado,
            @MappingTarget CertificadoDTO certificadoDTO);

    @AfterMapping
    default void afterUpdateCertificadoDTO(Certificado certificado,
            @MappingTarget CertificadoDTO certificadoDTO) {
        certificadoDTO.setAdministrativo(certificado.getAdministrativo() == null ? null : certificado.getAdministrativo().getIdAdministrativo());
        certificadoDTO.setMatricula(certificado.getMatricula() == null ? null : certificado.getMatricula().getCodMatricula());
    }

    @Mapping(target = "idCertificado", ignore = true)
    @Mapping(target = "administrativo", ignore = true)
    @Mapping(target = "matricula", ignore = true)
    Certificado updateCertificado(CertificadoDTO certificadoDTO,
            @MappingTarget Certificado certificado,
            @Context AdministrativoRepository administrativoRepository,
            @Context MatriculaRepository matriculaRepository);

    @AfterMapping
    default void afterUpdateCertificado(CertificadoDTO certificadoDTO,
            @MappingTarget Certificado certificado,
            @Context AdministrativoRepository administrativoRepository,
            @Context MatriculaRepository matriculaRepository) {
        final Administrativo administrativo = certificadoDTO.getAdministrativo() == null ? null : administrativoRepository.findById(certificadoDTO.getAdministrativo())
                .orElseThrow(() -> new NotFoundException("administrativo not found"));
        certificado.setAdministrativo(administrativo);
        final Matricula matricula = certificadoDTO.getMatricula() == null ? null : matriculaRepository.findById(certificadoDTO.getMatricula())
                .orElseThrow(() -> new NotFoundException("matricula not found"));
        certificado.setMatricula(matricula);
    }

}
