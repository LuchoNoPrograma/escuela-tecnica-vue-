package uap.edu.bo.escuela_tecnica.config;

import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.util.StreamUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import uap.edu.bo.escuela_tecnica.EscuelaTecnicaApplication;
import uap.edu.bo.escuela_tecnica.administrativo.AdministrativoRepository;
import uap.edu.bo.escuela_tecnica.calificacion.CalificacionRepository;
import uap.edu.bo.escuela_tecnica.categoria.CategoriaRepository;
import uap.edu.bo.escuela_tecnica.certificado.CertificadoRepository;
import uap.edu.bo.escuela_tecnica.comprobante_pago.ComprobantePagoRepository;
import uap.edu.bo.escuela_tecnica.configuracion_costo.ConfiguracionCostoRepository;
import uap.edu.bo.escuela_tecnica.criterio_eval.CriterioEvalRepository;
import uap.edu.bo.escuela_tecnica.cronograma_modulo.CronogramaModuloRepository;
import uap.edu.bo.escuela_tecnica.detalle_pago.DetallePagoRepository;
import uap.edu.bo.escuela_tecnica.docente.DocenteRepository;
import uap.edu.bo.escuela_tecnica.grupo.GrupoRepository;
import uap.edu.bo.escuela_tecnica.matricula.MatriculaRepository;
import uap.edu.bo.escuela_tecnica.modalidad.ModalidadRepository;
import uap.edu.bo.escuela_tecnica.modulo.ModuloRepository;
import uap.edu.bo.escuela_tecnica.monografia.MonografiaRepository;
import uap.edu.bo.escuela_tecnica.nivel.NivelRepository;
import uap.edu.bo.escuela_tecnica.observacion_monografia.ObservacionMonografiaRepository;
import uap.edu.bo.escuela_tecnica.ocupa.OcupaRepository;
import uap.edu.bo.escuela_tecnica.persona.PersonaRepository;
import uap.edu.bo.escuela_tecnica.plan_estudio.PlanEstudioRepository;
import uap.edu.bo.escuela_tecnica.plan_estudio_detalle.PlanEstudioDetalleRepository;
import uap.edu.bo.escuela_tecnica.plan_pago.PlanPagoRepository;
import uap.edu.bo.escuela_tecnica.preinscripcion.PreinscripcionRepository;
import uap.edu.bo.escuela_tecnica.programa.ProgramaRepository;
import uap.edu.bo.escuela_tecnica.programacion.ProgramacionRepository;
import uap.edu.bo.escuela_tecnica.revision_monografia.RevisionMonografiaRepository;
import uap.edu.bo.escuela_tecnica.rol.RolRepository;
import uap.edu.bo.escuela_tecnica.tarea.TareaRepository;
import uap.edu.bo.escuela_tecnica.titulacion.TitulacionRepository;
import uap.edu.bo.escuela_tecnica.usuario.UsuarioRepository;
import uap.edu.bo.escuela_tecnica.version.VersionRepository;


/**
 * Abstract base class to be extended by every IT test. Starts the Spring Boot context with a
 * Datasource connected to the Testcontainers Docker instance. The instance is reused for all tests,
 * with all data wiped out before each test.
 */
@SpringBootTest(
        classes = EscuelaTecnicaApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("it")
@Sql({"/data/clearAll.sql", "/data/rolData.sql", "/data/tareaData.sql", "/data/personaData.sql", "/data/usuarioData.sql"})
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIT {

    @ServiceConnection
    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:17.4");

    static {
        postgreSQLContainer.withReuse(true)
                .start();
    }

    @LocalServerPort
    public int serverPort;

    @Autowired
    public CalificacionRepository calificacionRepository;

    @Autowired
    public CertificadoRepository certificadoRepository;

    @Autowired
    public AdministrativoRepository administrativoRepository;

    @Autowired
    public ComprobantePagoRepository comprobantePagoRepository;

    @Autowired
    public ConfiguracionCostoRepository configuracionCostoRepository;

    @Autowired
    public CriterioEvalRepository criterioEvalRepository;

    @Autowired
    public DetallePagoRepository detallePagoRepository;

    @Autowired
    public DocenteRepository docenteRepository;

    @Autowired
    public ObservacionMonografiaRepository observacionMonografiaRepository;

    @Autowired
    public OcupaRepository ocupaRepository;

    @Autowired
    public PlanPagoRepository planPagoRepository;

    @Autowired
    public ProgramacionRepository programacionRepository;

    @Autowired
    public CronogramaModuloRepository cronogramaModuloRepository;

    @Autowired
    public PlanEstudioDetalleRepository planEstudioDetalleRepository;

    @Autowired
    public PlanEstudioRepository planEstudioRepository;

    @Autowired
    public ModuloRepository moduloRepository;

    @Autowired
    public NivelRepository nivelRepository;

    @Autowired
    public RevisionMonografiaRepository revisionMonografiaRepository;

    @Autowired
    public MonografiaRepository monografiaRepository;

    @Autowired
    public TitulacionRepository titulacionRepository;

    @Autowired
    public MatriculaRepository matriculaRepository;

    @Autowired
    public GrupoRepository grupoRepository;

    @Autowired
    public UsuarioRepository usuarioRepository;

    @Autowired
    public PersonaRepository personaRepository;

    @Autowired
    public TareaRepository tareaRepository;

    @Autowired
    public RolRepository rolRepository;

    @Autowired
    public VersionRepository versionRepository;

    @Autowired
    public ProgramaRepository programaRepository;

    @Autowired
    public CategoriaRepository categoriaRepository;

    @Autowired
    public ModalidadRepository modalidadRepository;

    @Autowired
    public PreinscripcionRepository preinscripcionRepository;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = serverPort;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @SneakyThrows
    public String readResource(final String resourceName) {
        return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), StandardCharsets.UTF_8);
    }

    public String administradorSistemasJwtSecurityConfigToken() {
        // user administradorSistemas, expires 2040-01-01
        return "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9." +
                "eyJzdWIiOiJhZG1pbmlzdHJhZG9yU2lzdGVtYXMiLCJyb2xlcyI6WyJBRE1JTklTVFJBRE9SX1NJU1RFTUFTIl0sImlzcyI6ImJvb3RpZnkiLCJpYXQiOjE3NDY4NTY0MDEsImV4cCI6MjIwODk4ODgwMH0." +
                "VvvSPFdFVseclK-e6Ui7ffMgP7oOuyFCmOunPOB74om6cTIgPNWLdyB-ujo1c-m0a4flXTVdaOSVZFac9av0CQ";
    }

    public String administrativoJwtSecurityConfigToken() {
        // user administrativo, expires 2040-01-01
        return "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9." +
                "eyJzdWIiOiJhZG1pbmlzdHJhdGl2byIsInJvbGVzIjpbIkFETUlOSVNUUkFUSVZPIl0sImlzcyI6ImJvb3RpZnkiLCJpYXQiOjE3NDY4NTY0MDEsImV4cCI6MjIwODk4ODgwMH0." +
                "kV0Py3ya0x9lKjR1AmoBmg04yvvH-wuCyIFTPnvTjKFPNES6EiI2ZJ6bqp2Fvniged-mifOKnogU1GtmQtQFEQ";
    }

    public String docenteJwtSecurityConfigToken() {
        // user docente, expires 2040-01-01
        return "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9." +
                "eyJzdWIiOiJkb2NlbnRlIiwicm9sZXMiOlsiRE9DRU5URSJdLCJpc3MiOiJib290aWZ5IiwiaWF0IjoxNzQ2ODU2NDAxLCJleHAiOjIyMDg5ODg4MDB9." +
                "W50RmI8R57gQ-B6lq09bzomsFqZxvRDGzIdkJBIqcmj8r-BrjFY9c1V1cO6R_u5FJDkPU51KPfrMRU_7slP_2g";
    }

    public String estudianteJwtSecurityConfigToken() {
        // user estudiante, expires 2040-01-01
        return "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9." +
                "eyJzdWIiOiJlc3R1ZGlhbnRlIiwicm9sZXMiOlsiRVNUVURJQU5URSJdLCJpc3MiOiJib290aWZ5IiwiaWF0IjoxNzQ2ODU2NDAxLCJleHAiOjIyMDg5ODg4MDB9." +
                "afk4Lz8bR8Q3uT_frJByKjExcYOWSXv4oYqq7O3coMpCOmInFXy_pjAfc-jTxqOPq_f53NnN0rXn_jTdeMw2vw";
    }

}
