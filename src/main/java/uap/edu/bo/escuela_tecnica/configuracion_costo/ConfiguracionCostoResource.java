package uap.edu.bo.escuela_tecnica.configuracion_costo;

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


@RestController
@RequestMapping(value = "/api/configuracionCostos", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class ConfiguracionCostoResource {

    private final ConfiguracionCostoService configuracionCostoService;
    private final ProgramaRepository programaRepository;

    public ConfiguracionCostoResource(final ConfiguracionCostoService configuracionCostoService,
            final ProgramaRepository programaRepository) {
        this.configuracionCostoService = configuracionCostoService;
        this.programaRepository = programaRepository;
    }

    @GetMapping
    public ResponseEntity<List<ConfiguracionCostoDTO>> getAllConfiguracionCostos() {
        return ResponseEntity.ok(configuracionCostoService.findAll());
    }

    @GetMapping("/{idConfiguracionCosto}")
    public ResponseEntity<ConfiguracionCostoDTO> getConfiguracionCosto(
            @PathVariable(name = "idConfiguracionCosto") final Long idConfiguracionCosto) {
        return ResponseEntity.ok(configuracionCostoService.get(idConfiguracionCosto));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createConfiguracionCosto(
            @RequestBody @Valid final ConfiguracionCostoDTO configuracionCostoDTO) {
        final Long createdIdConfiguracionCosto = configuracionCostoService.create(configuracionCostoDTO);
        return new ResponseEntity<>(createdIdConfiguracionCosto, HttpStatus.CREATED);
    }

    @PutMapping("/{idConfiguracionCosto}")
    public ResponseEntity<Long> updateConfiguracionCosto(
            @PathVariable(name = "idConfiguracionCosto") final Long idConfiguracionCosto,
            @RequestBody @Valid final ConfiguracionCostoDTO configuracionCostoDTO) {
        configuracionCostoService.update(idConfiguracionCosto, configuracionCostoDTO);
        return ResponseEntity.ok(idConfiguracionCosto);
    }

    @DeleteMapping("/{idConfiguracionCosto}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteConfiguracionCosto(
            @PathVariable(name = "idConfiguracionCosto") final Long idConfiguracionCosto) {
        configuracionCostoService.delete(idConfiguracionCosto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/programaValues")
    public ResponseEntity<Map<Long, String>> getProgramaValues() {
        return ResponseEntity.ok(programaRepository.findAll(Sort.by("idPrograma"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Programa::getIdPrograma, Programa::getNombrePrograma)));
    }

}
