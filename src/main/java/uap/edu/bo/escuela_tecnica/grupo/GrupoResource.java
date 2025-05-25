package uap.edu.bo.escuela_tecnica.grupo;

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
import uap.edu.bo.escuela_tecnica.programa.Programa;
import uap.edu.bo.escuela_tecnica.programa.ProgramaRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;
import uap.edu.bo.escuela_tecnica.version.Version;
import uap.edu.bo.escuela_tecnica.version.VersionRepository;


@RestController
@RequestMapping(value = "/api/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class GrupoResource {

    private final GrupoService grupoService;
    private final VersionRepository versionRepository;
    private final ProgramaRepository programaRepository;

    public GrupoResource(final GrupoService grupoService, final VersionRepository versionRepository,
            final ProgramaRepository programaRepository) {
        this.grupoService = grupoService;
        this.versionRepository = versionRepository;
        this.programaRepository = programaRepository;
    }

    @GetMapping
    public ResponseEntity<List<GrupoDTO>> getAllGrupos() {
        return ResponseEntity.ok(grupoService.findAll());
    }

    @GetMapping("/{idGrupo}")
    public ResponseEntity<GrupoDTO> getGrupo(@PathVariable(name = "idGrupo") final Long idGrupo) {
        return ResponseEntity.ok(grupoService.get(idGrupo));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createGrupo(@RequestBody @Valid final GrupoDTO grupoDTO) {
        final Long createdIdGrupo = grupoService.create(grupoDTO);
        return new ResponseEntity<>(createdIdGrupo, HttpStatus.CREATED);
    }

    @PutMapping("/{idGrupo}")
    public ResponseEntity<Long> updateGrupo(@PathVariable(name = "idGrupo") final Long idGrupo,
            @RequestBody @Valid final GrupoDTO grupoDTO) {
        grupoService.update(idGrupo, grupoDTO);
        return ResponseEntity.ok(idGrupo);
    }

    @DeleteMapping("/{idGrupo}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGrupo(@PathVariable(name = "idGrupo") final Long idGrupo) {
        final ReferencedWarning referencedWarning = grupoService.getReferencedWarning(idGrupo);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        grupoService.delete(idGrupo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/versionValues")
    public ResponseEntity<Map<Integer, String>> getVersionValues() {
        return ResponseEntity.ok(versionRepository.findAll(Sort.by("idVersion"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Version::getIdVersion, Version::getCodVersion)));
    }

    @GetMapping("/programaValues")
    public ResponseEntity<Map<Long, String>> getProgramaValues() {
        return ResponseEntity.ok(programaRepository.findAll(Sort.by("idPrograma"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Programa::getIdPrograma, Programa::getNombrePrograma)));
    }

}
