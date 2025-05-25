package uap.edu.bo.escuela_tecnica.categoria;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
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
import uap.edu.bo.escuela_tecnica.util.ReferencedException;
import uap.edu.bo.escuela_tecnica.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/categorias", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class CategoriaResource {

    private final CategoriaService categoriaService;

    public CategoriaResource(final CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> getCategoria(
            @PathVariable(name = "idCategoria") final Long idCategoria) {
        return ResponseEntity.ok(categoriaService.get(idCategoria));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCategoria(
            @RequestBody @Valid final CategoriaDTO categoriaDTO) {
        final Long createdIdCategoria = categoriaService.create(categoriaDTO);
        return new ResponseEntity<>(createdIdCategoria, HttpStatus.CREATED);
    }

    @PutMapping("/{idCategoria}")
    public ResponseEntity<Long> updateCategoria(
            @PathVariable(name = "idCategoria") final Long idCategoria,
            @RequestBody @Valid final CategoriaDTO categoriaDTO) {
        categoriaService.update(idCategoria, categoriaDTO);
        return ResponseEntity.ok(idCategoria);
    }

    @DeleteMapping("/{idCategoria}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCategoria(
            @PathVariable(name = "idCategoria") final Long idCategoria) {
        final ReferencedWarning referencedWarning = categoriaService.getReferencedWarning(idCategoria);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        categoriaService.delete(idCategoria);
        return ResponseEntity.noContent().build();
    }

}
