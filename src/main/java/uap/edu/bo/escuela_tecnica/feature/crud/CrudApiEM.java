package uap.edu.bo.escuela_tecnica.feature.crud;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uap.edu.bo.escuela_tecnica.grupo.Grupo;
import uap.edu.bo.escuela_tecnica.matricula.Matricula;
import uap.edu.bo.escuela_tecnica.persona.Persona;
import uap.edu.bo.escuela_tecnica.util.NotFoundException;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class CrudApiEM {
    private final CrudRepositoryEM crudRepositoryEM;

    @GetMapping("/publico/persona")
    public ResponseEntity<?> listarPersonas(){
        return ResponseEntity.ok(crudRepositoryEM.listarTodos(Persona.class));
    }


    @GetMapping("/publico/persona/post")
    public ResponseEntity<?> guardarPersona(){
        Persona persona = new Persona();
        persona.setNombre("Carlitos");
        persona.setApMaterno("Choquehuanca");
        persona.setApPaterno("Heredia");
        persona.setCi("12039212344");
        persona.setNroCelular("68485483");
        return ResponseEntity.ok(crudRepositoryEM.guardar(persona));
    }

    @GetMapping("/publico/persona/buscar/{idPersona}")
    public ResponseEntity<?> buscarPersona(@PathVariable Long idPersona){
        return ResponseEntity.ok(crudRepositoryEM.buscarPorId(Persona.class, idPersona));
    }

    @GetMapping("/publico/persona/buscar/{idPersona}/sql")
    public ResponseEntity<?> buscarPersonaSQL(@PathVariable Long idPersona){
        String sql = """
            select id_persona, nombre, ap_paterno, ap_materno, ci from persona
            where id_persona = ?1
            """;

        return ResponseEntity.ok(crudRepositoryEM.ejecutarActualizacionNativa(sql, idPersona));
    }

    @Transactional
    @GetMapping("/publico/matricular")
    public ResponseEntity<?> guardarMatricula(){
        Persona persona = new Persona();
        persona.setNombre("Carlitos");
        persona.setApMaterno("Condirito");
        persona.setApPaterno("Atahuallpa");
        persona.setCi("195921598");
        persona.setNroCelular("684854242");
        Persona persistido = crudRepositoryEM.guardar(persona);

        Grupo grupo =  crudRepositoryEM.buscarPorId(Grupo.class, 1L).orElse(null);

        Matricula matricula = new Matricula();
        matricula.setEstMatricula("ACTIVO");
        matricula.setFecMatriculacion(LocalDate.now());
        matricula.setPersona(persistido);
        matricula.setGrupo(grupo);

        return ResponseEntity.ok(crudRepositoryEM.guardar(matricula));
    }

    @GetMapping("/publico/matricular/lista")
    public ResponseEntity<?> listarMatriculas(){
      return ResponseEntity.ok(crudRepositoryEM.listarTodos(Matricula.class));
    }

    @GetMapping("/publico/matricular/lista/sql")
    public ResponseEntity<?> listarMatriculasSQL(){
        String sql = """
            select
            mtr.cod_matricula, prs.nombre, prs.ap_paterno, prs.ap_materno, prs.ci
            from matricula mtr
            inner join persona prs on prs.id_persona = mtr.fk_id_persona
            """;
        return ResponseEntity.ok(crudRepositoryEM.ejecutarConsultaNativa(sql));
    }

    @GetMapping("/publico/matricular/lista/jpql")
    public ResponseEntity<?> listarMatriculasJPQL(){
        String sql = """
            select mtr from Matricula mtr
            join fetch mtr.grupo
            join fetch mtr.persona
            """;
        return ResponseEntity.ok(crudRepositoryEM.ejecutarConsulta(sql,  Matricula.class));
    }

    @Transactional
    @GetMapping("/publico/persona/editar")
    public ResponseEntity<?> editarPersona(){
        Persona persona = crudRepositoryEM.buscarPorId(Persona.class, 7L).orElseThrow();
        persona.setNombre("Miriam");
        persona.setApPaterno("Condorito");
        persona.setApMaterno("Sanchez");

        return ResponseEntity.ok(crudRepositoryEM.guardar(persona));
    }
}
