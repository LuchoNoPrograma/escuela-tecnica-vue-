package uap.edu.bo.escuela_tecnica.feature.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class CrudApi {
    private final JdbcClient jdbc;

    @GetMapping("/api/crud/page/{tabla}")
    public Map<String, Object> listarPage(
        @PathVariable String tabla,
        @RequestParam(defaultValue="") String q,
        @RequestParam(defaultValue="0") int page,
        @RequestParam(defaultValue="10") int size
    ) {
        Set<String> TABLAS_PERMITIDAS = Set.of("rol", "tarea", "categoria", "persona");
        if (!TABLAS_PERMITIDAS.contains(tabla)) {
            throw new IllegalArgumentException("Tabla no permitida: " + tabla);
        }

        int offset = page * size;
        List<String> columnas = columnasTexto(tabla);

        String filtro = q.isBlank()
            ? "1=1"
            : "(" + String.join(" OR ", columnas.stream()
            .map(col -> "cast(" + col + " as text) ILIKE '%' || ? || '%'")
            .toList()) + ")";

        String sql = "SELECT * FROM " + tabla + " WHERE " + filtro + " LIMIT ? OFFSET ?";

        Object[] params = q.isBlank()
            ? new Object[]{size, offset}
            : Stream.concat(Collections.nCopies(columnas.size(), q).stream(), Stream.of(size, offset)).toArray();

        List<Map<String, Object>> content = jdbc.sql(sql)
            .params(params)
            .query().listOfRows();

        // Conteo total para paginación
        String sqlCount = "SELECT count(*) FROM " + tabla + " WHERE " + filtro;
        Object[] countParams = q.isBlank()
            ? new Object[]{}
            : Collections.nCopies(columnas.size(), q).toArray();

        Integer totalElements = jdbc.sql(sqlCount)
            .params(countParams)
            .query(Integer.class).single();

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return Map.of(
            "content", content,
            "totalElements", totalElements,
            "totalPages", totalPages,
            "number", page,
            "size", size
        );
    }



    // Utilidad para obtener columnas texto de la tabla usando el catálogo de Postgres
    private List<String> columnasTexto(String tabla) {
        return jdbc.sql("""
        SELECT column_name FROM information_schema.columns
        WHERE table_name = ? AND data_type IN ('character varying','text','varchar','char')
    """).params(tabla).query(String.class).list();
    }
}
