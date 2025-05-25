package uap.edu.bo.escuela_tecnica.programa;

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
import uap.edu.bo.escuela_tecnica.categoria.Categoria;
import uap.edu.bo.escuela_tecnica.categoria.CategoriaRepository;
import uap.edu.bo.escuela_tecnica.modalidad.Modalidad;
import uap.edu.bo.escuela_tecnica.modalidad.ModalidadRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/programas", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class ProgramaResource {

    private final ProgramaService programaService;
    private final ModalidadRepository modalidadRepository;
    private final CategoriaRepository categoriaRepository;

    public ProgramaResource(final ProgramaService programaService,
            final ModalidadRepository modalidadRepository,
            final CategoriaRepository categoriaRepository) {
        this.programaService = programaService;
        this.modalidadRepository = modalidadRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProgramaDTO>> getAllProgramas() {
        return ResponseEntity.ok(programaService.findAll());
    }

    @GetMapping("/{idPrograma}")
    public ResponseEntity<ProgramaDTO> getPrograma(
            @PathVariable(name = "idPrograma") final Long idPrograma) {
        return ResponseEntity.ok(programaService.get(idPrograma));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPrograma(@RequestBody @Valid final ProgramaDTO programaDTO) {
        final Long createdIdPrograma = programaService.create(programaDTO);
        return new ResponseEntity<>(createdIdPrograma, HttpStatus.CREATED);
    }

    @PutMapping("/{idPrograma}")
    public ResponseEntity<Long> updatePrograma(
            @PathVariable(name = "idPrograma") final Long idPrograma,
            @RequestBody @Valid final ProgramaDTO programaDTO) {
        programaService.update(idPrograma, programaDTO);
        return ResponseEntity.ok(idPrograma);
    }

    @DeleteMapping("/{idPrograma}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePrograma(
            @PathVariable(name = "idPrograma") final Long idPrograma) {
        final ReferencedWarning referencedWarning = programaService.getReferencedWarning(idPrograma);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        programaService.delete(idPrograma);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/modalidadValues")
    public ResponseEntity<Map<Long, Long>> getModalidadValues() {
        return ResponseEntity.ok(modalidadRepository.findAll(Sort.by("idModalidad"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Modalidad::getIdModalidad, Modalidad::getIdModalidad)));
    }

    @GetMapping("/categoriaValues")
    public ResponseEntity<Map<Long, Long>> getCategoriaValues() {
        return ResponseEntity.ok(categoriaRepository.findAll(Sort.by("idCategoria"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Categoria::getIdCategoria, Categoria::getIdCategoria)));
    }

}
