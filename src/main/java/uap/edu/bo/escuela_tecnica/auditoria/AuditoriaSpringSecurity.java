package uap.edu.bo.escuela_tecnica.auditoria;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uap.edu.bo.escuela_tecnica.security.JwtSecurityConfigUserDetails;
import uap.edu.bo.escuela_tecnica.usuario.Usuario;
import uap.edu.bo.escuela_tecnica.usuario.UsuarioRepository;

import java.util.Optional;

@Component
public class AuditoriaSpringSecurity implements AuditorAware<Long> {
    @Autowired
    private UsuarioRepository usuarioRepository; // o el repositorio que use tu entidad Usuario

    @Override
    @NonNull
    public Optional<Long> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Si no está autenticada o no hay auth, devolvemos el auditor por defecto = 1L
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.of(1L);
        }

        Object principal = auth.getPrincipal();
        Long auditorId = 1L; // valor por defecto

        if (principal instanceof JwtSecurityConfigUserDetails uds) {
            // Caso 1: si el principal es tu propio UserDetails, ya trae el ID
            auditorId = uds.getIdUsuario();
        }
        /*else if (principal instanceof String username && !username.isBlank()) {
            // Caso 2: principal es String (username), obtenemos solo el ID:
            Long id = usuarioRepository.findIdByNombreUsuarioIgnoreCase(username);
            auditorId = (id != null) ? id : 1L;
        }*/

        return Optional.of(auditorId);
    }
}
