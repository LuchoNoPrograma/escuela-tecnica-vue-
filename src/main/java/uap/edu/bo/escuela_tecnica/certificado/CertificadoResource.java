package uap.edu.bo.escuela_tecnica.certificado;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uap.edu.bo.escuela_tecnica.administrativo.Administrativo;
import uap.edu.bo.escuela_tecnica.administrativo.AdministrativoRepository;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;


@RestController
@RequestMapping(value = "/api/certificados", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class CertificadoResource {

    private final CertificadoService certificadoService;
    private final AdministrativoRepository administrativoRepository;
    private final MatriculaRepository matriculaRepository;

    public CertificadoResource(final CertificadoService certificadoService,
            final AdministrativoRepository administrativoRepository,
            final MatriculaRepository matriculaRepository) {
        this.certificadoService = certificadoService;
        this.administrativoRepository = administrativoRepository;
        this.matriculaRepository = matriculaRepository;
    }

    @GetMapping
    public ResponseEntity<List<CertificadoDTO>> getAllCertificados() {
        return ResponseEntity.ok(certificadoService.findAll());
    }

    @GetMapping("/{idCertificado}")
    public ResponseEntity<CertificadoDTO> getCertificado(
            @PathVariable(name = "idCertificado") final Long idCertificado) {
        return ResponseEntity.ok(certificadoService.get(idCertificado));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCertificado(
            @RequestBody @Valid final CertificadoDTO certificadoDTO) {
        final Long createdIdCertificado = certificadoService.create(certificadoDTO);
        return new ResponseEntity<>(createdIdCertificado, HttpStatus.CREATED);
    }

    @PutMapping("/{idCertificado}")
    public ResponseEntity<Long> updateCertificado(
            @PathVariable(name = "idCertificado") final Long idCertificado,
            @RequestBody @Valid final CertificadoDTO certificadoDTO) {
        certificadoService.update(idCertificado, certificadoDTO);
        return ResponseEntity.ok(idCertificado);
    }

    @DeleteMapping("/{idCertificado}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCertificado(
            @PathVariable(name = "idCertificado") final Long idCertificado) {
        certificadoService.delete(idCertificado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/administrativoValues")
    public ResponseEntity<Map<Long, Long>> getAdministrativoValues() {
        return ResponseEntity.ok(administrativoRepository.findAll(Sort.by("idAdministrativo"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Administrativo::getIdAdministrativo, Administrativo::getIdAdministrativo)));
    }

    @GetMapping("/matriculaValues")
    public ResponseEntity<Map<Long, String>> getMatriculaValues() {
        return ResponseEntity.ok(matriculaRepository.findAll(Sort.by("codMatricula"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Matricula::getCodMatricula, Matricula::getEstMatricula)));
    }

}
