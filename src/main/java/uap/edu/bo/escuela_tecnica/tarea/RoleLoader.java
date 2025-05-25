package uap.edu.bo.escuela_tecnica.tarea;

import java.time.OffsetDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uap.edu.bo.escuela_tecnica.rol.Rol;
import uap.edu.bo.escuela_tecnica.rol.RolRepository;
import uap.edu.bo.escuela_tecnica.security.UserRoles;
import uap.edu.bo.escuela_tecnica.usuario.Usuario;
import uap.edu.bo.escuela_tecnica.usuario.UsuarioRepository;
import uap.edu.bo.escuela_tecnica.persona.Persona;
import uap.edu.bo.escuela_tecnica.persona.PersonaRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class RoleLoader implements ApplicationRunner {

    private final TareaRepository tareaRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(final ApplicationArguments args) {
        if (tareaRepository.count() != 0 || usuarioRepository.count() != 0) {
            return;
        }

        log.info("Inicializando usuario administrador, roles y tareas");

        // 1. Creamos una Persona para el admin
        Persona adminPersona = new Persona();
        adminPersona.setNombre("ADMIN");
        adminPersona.setApPaterno("SISTEMAS");
        adminPersona.setApMaterno("ROOT");
        adminPersona.setCi("12345678");
        adminPersona.setNroCelular("123123123");
        adminPersona.setIdUsuReg(0L); // <= Seteado aquí y bien
        personaRepository.save(adminPersona);

        // 2. Creamos el Usuario administrador
        Usuario adminUsuario = new Usuario();
        adminUsuario.setNombreUsuario("admin");

        // HASH PARA "admin", generado con bcrypt (clave: admin)
        adminUsuario.setContrasenaHash(passwordEncoder.encode("admin"));

        adminUsuario.setEstUsuario("ACTIVO");
        adminUsuario.setPersona(adminPersona);
        adminUsuario.setIdUsuReg(0L); // <= ¡ESTE FALTABA! (Es para el usuario)
        adminUsuario = usuarioRepository.save(adminUsuario);


        // 4. Creamos los roles
        Rol adminRol = crearRolSiNoExiste(UserRoles.ADMINISTRADOR_SISTEMAS, "Activo", "Rol para administradores del sistema");
        Rol administrativoRol = crearRolSiNoExiste(UserRoles.ADMINISTRATIVO, "Activo", "Rol para administrativos");
        Rol docenteRol = crearRolSiNoExiste(UserRoles.DOCENTE, "Activo", "Rol para docentes");
        Rol estudianteRol = crearRolSiNoExiste(UserRoles.ESTUDIANTE, "Activo", "Rol para estudiantes");

        // 5. Creamos las tareas y asignamos los roles correspondientes
        crearTarea(UserRoles.ADMINISTRADOR_SISTEMAS, OffsetDateTime.now(), true, adminRol);
        crearTarea(UserRoles.ADMINISTRATIVO, OffsetDateTime.now(), true, administrativoRol);
        crearTarea(UserRoles.DOCENTE, OffsetDateTime.now(), true, docenteRol);
        crearTarea(UserRoles.ESTUDIANTE, OffsetDateTime.now(), true, estudianteRol);


        adminUsuario = usuarioRepository.findById(adminUsuario.getIdUsuario()).orElseThrow(RuntimeException::new);
        adminUsuario.setListaTareasAsignadas(adminRol.getListaTareas());
        usuarioRepository.save(adminUsuario);
        log.info("Datos iniciales creados exitosamente");
    }

    private Rol crearRolSiNoExiste(String nombre, String estado, String descripcion) {
        return rolRepository.findByNombre(nombre)
            .orElseGet(() -> {
                Rol rol = new Rol();
                rol.setNombre(nombre);
                rol.setEstCargo(estado);
                rol.setDescripcionCargo(descripcion);
                rol.setIdUsuReg(0L);
                return rolRepository.save(rol);
            });
    }

    private void crearTarea(String nombreTarea, OffsetDateTime fechaReg, boolean estado, Rol rol) {
        Tarea tarea = new Tarea();
        tarea.setNombreTarea(nombreTarea);
        tarea.setFecReg(fechaReg);
        tarea.setEstTarea(estado);
        tarea.setRol(rol);
        tarea.setIdUsuReg(0L);
        tareaRepository.save(tarea);
    }
}
