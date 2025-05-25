package uap.edu.bo.escuela_tecnica.tarea;

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
import uap.edu.bo.escuela_tecnica.rol.Rol;
import uap.edu.bo.escuela_tecnica.rol.RolRepository;
import uap.edu.bo.escuela_tecnica.util.CustomCollectors;


@RestController
@RequestMapping(value = "/api/tareas", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "bearer-jwt")
public class TareaResource {

    private final TareaService tareaService;
    private final RolRepository rolRepository;

    public TareaResource(final TareaService tareaService, final RolRepository rolRepository) {
        this.tareaService = tareaService;
        this.rolRepository = rolRepository;
    }

    @GetMapping
    public ResponseEntity<List<TareaDTO>> getAllTareas() {
        return ResponseEntity.ok(tareaService.findAll());
    }

    @GetMapping("/{idTarea}")
    public ResponseEntity<TareaDTO> getTarea(@PathVariable(name = "idTarea") final Long idTarea) {
        return ResponseEntity.ok(tareaService.get(idTarea));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTarea(@RequestBody @Valid final TareaDTO tareaDTO) {
        final Long createdIdTarea = tareaService.create(tareaDTO);
        return new ResponseEntity<>(createdIdTarea, HttpStatus.CREATED);
    }

    @PutMapping("/{idTarea}")
    public ResponseEntity<Long> updateTarea(@PathVariable(name = "idTarea") final Long idTarea,
            @RequestBody @Valid final TareaDTO tareaDTO) {
        tareaService.update(idTarea, tareaDTO);
        return ResponseEntity.ok(idTarea);
    }

    @DeleteMapping("/{idTarea}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTarea(@PathVariable(name = "idTarea") final Long idTarea) {
        tareaService.delete(idTarea);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/agrupaValues")
    public ResponseEntity<Map<Long, String>> getAgrupaValues() {
        return ResponseEntity.ok(rolRepository.findAll(Sort.by("idPermiso"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Rol::getIdRol, Rol::getNombre)));
    }

}
