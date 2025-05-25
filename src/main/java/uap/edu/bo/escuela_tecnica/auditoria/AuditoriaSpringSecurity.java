package uap.edu.bo.escuela_tecnica.auditoria;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uap.edu.bo.escuela_tecnica.usuario.Usuario;

import java.util.Optional;

@Component
public class AuditoriaSpringSecurity implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if (auth != null && auth.isAuthenticated()) {
             return Optional.of(((Usuario) auth.getPrincipal()).getIdUsuario());
         }
         return Optional.of(1L);
    }
}
