package uap.edu.bo.escuela_tecnica.feature.crud;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CrudRepository {
    private final JdbcClient jdbcClient;


    // Cache de tablas permitidas - se carga automáticamente!
    private Set<String> tablasPermitidas = new HashSet<>();

    // Cache de columnas por tabla
    private Map<String, Set<String>> columnasPorTabla = new HashMap<>();

    // Patrón para validar nombres
    private static final Pattern PATRON_IDENTIFICADOR = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");

    // Palabras peligrosas
    private static final Set<String> PALABRAS_PELIGROSAS = Set.of(
        "drop", "delete", "truncate", "alter", "create",
        "exec", "execute", "script", "javascript", "function"
    );

    /**
     * Inicializa el cache de tablas al arrancar
     */
    @PostConstruct
    public void inicializarCache() {
        cargarTablasDelSchema();
    }

    /**
     * Carga todas las tablas del schema público automáticamente
     */
    private void cargarTablasDelSchema() {
        String sql = """
            SELECT table_name
            FROM information_schema.tables
            WHERE table_schema = 'public'
            AND table_type = 'BASE TABLE'
        """;

        List<String> tablas = jdbcClient.sql(sql)
            .query(String.class)
            .list();

        this.tablasPermitidas = new HashSet<>(tablas);

        // Log para debug
        System.out.println("Tablas cargadas automáticamente: " + tablasPermitidas.size());
    }

    /**
     * Carga las columnas de una tabla (con cache)
     */
    private Set<String> obtenerColumnasDeTabla(String nombreTabla) {
        // Si ya está en cache, retornar
        if (columnasPorTabla.containsKey(nombreTabla)) {
            return columnasPorTabla.get(nombreTabla);
        }

        String sql = """
            SELECT column_name
            FROM information_schema.columns
            WHERE table_schema = 'public'
            AND table_name = :tabla:
        """;

        List<String> columnas = jdbcClient.sql(sql)
            .param("tabla", nombreTabla)
            .query(String.class)
            .list();

        Set<String> setColumnas = new HashSet<>(columnas);
        columnasPorTabla.put(nombreTabla, setColumnas);

        return setColumnas;
    }

    /**
     * Refresca el cache de tablas (por si agregan tablas nuevas)
     */
    public void refrescarCacheTablas() {
        cargarTablasDelSchema();
        columnasPorTabla.clear(); // Limpiar cache de columnas también
    }

    /**
     * Valida y sanitiza el nombre de tabla
     */
    private String validarNombreTabla(String nombreTabla) {
        if (nombreTabla == null || nombreTabla.isEmpty()) {
            throw new IllegalArgumentException("Nombre de tabla no puede ser vacío");
        }

        // Convertir a minúsculas
        String tablaMinusculas = nombreTabla.toLowerCase();

        // Verificar que existe en el schema
        if (!tablasPermitidas.contains(tablaMinusculas)) {
            throw new SecurityException("Tabla no existe en el schema: " + nombreTabla);
        }

        // Validar formato
        if (!PATRON_IDENTIFICADOR.matcher(tablaMinusculas).matches()) {
            throw new SecurityException("Nombre de tabla inválido: " + nombreTabla);
        }

        return tablaMinusculas;
    }

    /**
     * Valida nombres de columnas contra el schema real
     */
    private void validarNombresColumnas(String nombreTabla, Collection<String> columnas) {
        if (columnas == null || columnas.isEmpty()) return;

        Set<String> columnasReales = obtenerColumnasDeTabla(nombreTabla);

        for (String columna : columnas) {
            // Validar formato
            if (!PATRON_IDENTIFICADOR.matcher(columna).matches()) {
                throw new SecurityException("Nombre de columna inválido: " + columna);
            }

            // Verificar que existe en la tabla
            if (!columnasReales.contains(columna.toLowerCase())) {
                throw new SecurityException(
                    String.format("Columna '%s' no existe en tabla '%s'", columna, nombreTabla)
                );
            }

            // Verificar palabras peligrosas
            String columnaMinusculas = columna.toLowerCase();
            for (String palabraPeligrosa : PALABRAS_PELIGROSAS) {
                if (columnaMinusculas.contains(palabraPeligrosa)) {
                    throw new SecurityException("Columna contiene palabra peligrosa: " + columna);
                }
            }
        }
    }

    /**
     * SELECT seguro con validación automática
     */
    public List<Map<String, Object>> seleccionar(
        String nombreTabla,
        List<String> columnas,
        Map<String, Object> condicionesWhere,
        String ordenarPor,
        Integer limite) {

        // Validación de seguridad
        String tablaSegura = validarNombreTabla(nombreTabla);

        // Validar columnas si se especifican
        if (columnas != null && !columnas.isEmpty()) {
            validarNombresColumnas(tablaSegura, columnas);
        }

        // Validar columnas del WHERE
        if (condicionesWhere != null) {
            validarNombresColumnas(tablaSegura, condicionesWhere.keySet());
        }

        // Validar ORDER BY
        if (ordenarPor != null) {
            String[] partes = ordenarPor.split("\\s+");
            if (partes.length > 0) {
                validarNombresColumnas(tablaSegura, List.of(partes[0]));
            }
            if (partes.length == 2 && !partes[1].equalsIgnoreCase("ASC") && !partes[1].equalsIgnoreCase("DESC")) {
                throw new SecurityException("Dirección ORDER BY inválida: " + partes[1]);
            }
        }

        // Construir query
        StringBuilder sql = new StringBuilder("SELECT ");

        if (columnas != null && !columnas.isEmpty()) {
            sql.append(String.join(", ", columnas));
        } else {
            sql.append("*");
        }

        sql.append(" FROM ").append(tablaSegura);

        // WHERE
        if (condicionesWhere != null && !condicionesWhere.isEmpty()) {
            sql.append(" WHERE ");
            List<String> condiciones = new ArrayList<>();
            condicionesWhere.forEach((clave, valor) -> {
                if (valor == null) {
                    condiciones.add(clave + " IS NULL");
                } else if (valor instanceof List) {
                    condiciones.add(clave + " = ANY(:" + clave + ")");
                } else {
                    condiciones.add(clave + " = :" + clave);
                }
            });
            sql.append(String.join(" AND ", condiciones));
        }

        if (ordenarPor != null) {
            sql.append(" ORDER BY ").append(ordenarPor);
        }

        if (limite != null && limite > 0 && limite <= 1000) {
            sql.append(" LIMIT ").append(limite);
        }

        var consulta = jdbcClient.sql(sql.toString());

        if (condicionesWhere != null) {
            condicionesWhere.forEach((clave, valor) -> {
                if (valor != null) {
                    consulta.param(clave, valor);
                }
            });
        }

        return consulta.query().listOfRows();
    }

    /**
     * INSERT seguro con validación de columnas
     */
    @Transactional
    public Object insertar(String nombreTabla, Map<String, Object> datos, String columnaRetorno) {
        // Validación
        String tablaSegura = validarNombreTabla(nombreTabla);
        validarNombresColumnas(tablaSegura, datos.keySet());

        if (columnaRetorno != null) {
            validarNombresColumnas(tablaSegura, List.of(columnaRetorno));
        }

        // Filtrar valores peligrosos
        Map<String, Object> datosFiltrados = datos.entrySet().stream()
            .filter(e -> e.getValue() != null)
            .filter(e -> esValorPeligroso(e.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (datosFiltrados.isEmpty()) {
            throw new IllegalArgumentException("No hay datos válidos para insertar");
        }

        List<String> columnas = new ArrayList<>(datosFiltrados.keySet());
        List<String> parametros = columnas.stream()
            .map(col -> ":" + col)
            .collect(Collectors.toList());

        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tablaSegura).append(" (");
        sql.append(String.join(", ", columnas));
        sql.append(") VALUES (");
        sql.append(String.join(", ", parametros));
        sql.append(")");

        if (columnaRetorno != null) {
            sql.append(" RETURNING ").append(columnaRetorno);
        }

        var consulta = jdbcClient.sql(sql.toString());
        datosFiltrados.forEach(consulta::param);

        if (columnaRetorno != null) {
            if (columnaRetorno.contains(",")) {
                return consulta.query().singleRow();
            } else {
                return consulta.query(Long.class).single();
            }
        } else {
            consulta.update();
            return null;
        }
    }

    /**
     * UPDATE seguro con validación completa
     */
    @Transactional
    public int actualizar(String nombreTabla, Map<String, Object> datos, Map<String, Object> condicionesWhere) {
        // Validación
        String tablaSegura = validarNombreTabla(nombreTabla);
        validarNombresColumnas(tablaSegura, datos.keySet());
        validarNombresColumnas(tablaSegura, condicionesWhere.keySet());

        if (datos.isEmpty()) {
            throw new IllegalArgumentException("No hay datos para actualizar");
        }

        if (condicionesWhere.isEmpty()) {
            throw new IllegalArgumentException("WHERE es obligatorio por seguridad");
        }

        // Filtrar datos peligrosos
        Map<String, Object> datosSeguro = datos.entrySet().stream()
            .filter(e -> esValorPeligroso(e.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(tablaSegura).append(" SET ");

        List<String> clausulasSet = datosSeguro.keySet().stream()
            .map(col -> col + " = :" + col)
            .collect(Collectors.toList());
        sql.append(String.join(", ", clausulasSet));

        sql.append(" WHERE ");
        List<String> clausulasWhere = condicionesWhere.keySet().stream()
            .map(col -> col + " = :where_" + col)
            .collect(Collectors.toList());
        sql.append(String.join(" AND ", clausulasWhere));

        var consulta = jdbcClient.sql(sql.toString());
        datosSeguro.forEach(consulta::param);
        condicionesWhere.forEach((clave, valor) ->
            consulta.param("where_" + clave, valor)
        );

        return consulta.update();
    }

    /**
     * Eliminar lógico seguro
     */
    @Transactional
    public int eliminarLogico(String nombreTabla, Map<String, Object> condicionesWhere,
                              String columnaEstado, String valorEliminado) {

        String columnaEstadoFinal = columnaEstado != null ? columnaEstado : "_estado";
        String valorEliminadoFinal = valorEliminado != null ? valorEliminado : "ELIMINADO";

        // Validar que la columna de estado existe
        String tablaSegura = validarNombreTabla(nombreTabla);
        validarNombresColumnas(tablaSegura, List.of(columnaEstadoFinal));

        // Validar valor
        if (!valorEliminadoFinal.matches("^[A-Z_]+$")) {
            throw new SecurityException("Valor de estado inválido: " + valorEliminadoFinal);
        }

        Map<String, Object> datosActualizar = new HashMap<>();
        datosActualizar.put(columnaEstadoFinal, valorEliminadoFinal);
        datosActualizar.put("_modificacion", LocalDateTime.now());

        return actualizar(nombreTabla, datosActualizar, condicionesWhere);
    }

    /**
     * Verifica si un valor es potencialmente peligroso
     */
    private boolean esValorPeligroso(Object valor) {
        if (valor == null) return true;

        String valorStr = valor.toString().toLowerCase();

        return PALABRAS_PELIGROSAS.stream()
            .noneMatch(valorStr::contains);
    }

    /**
     * Buscar por ID
     */
    public Map<String, Object> buscarPorId(String nombreTabla, String columnaId, Object valorId) {
        List<Map<String, Object>> resultados = seleccionar(
            nombreTabla,
            null,
            Map.of(columnaId, valorId),
            null,
            1
        );

        return resultados.isEmpty() ? null : resultados.get(0);
    }

    /**
     * Verificar si existe
     */
    public boolean existe(String nombreTabla, Map<String, Object> condicionesWhere) {
        String tablaSegura = validarNombreTabla(nombreTabla);

        String sql = "SELECT COUNT(*) > 0 FROM " + tablaSegura;

        if (condicionesWhere != null && !condicionesWhere.isEmpty()) {
            validarNombresColumnas(tablaSegura, condicionesWhere.keySet());

            sql += " WHERE ";
            List<String> condiciones = condicionesWhere.keySet().stream()
                .map(clave -> clave + " = :" + clave)
                .collect(Collectors.toList());
            sql += String.join(" AND ", condiciones);
        }

        var consulta = jdbcClient.sql(sql);

        if (condicionesWhere != null) {
            condicionesWhere.forEach(consulta::param);
        }

        return consulta.query(Boolean.class).single();
    }

    /**
     * Obtener las tablas disponibles (útil para debug)
     */
    public Set<String> obtenerTablasDisponibles() {
        return new HashSet<>(tablasPermitidas);
    }
}
