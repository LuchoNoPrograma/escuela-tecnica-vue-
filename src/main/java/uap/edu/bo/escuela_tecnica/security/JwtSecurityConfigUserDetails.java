package uap.edu.bo.escuela_tecnica.security;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


/**
 * Extension of Spring Security User class to store additional data.
 */
@Getter
public class JwtSecurityConfigUserDetails extends User {

    private final Long idUsuario;

    public JwtSecurityConfigUserDetails(final Long idUsuario, final String username,
            final String hash, final Collection<? extends GrantedAuthority> authorities) {
        super(username, hash, authorities);
        this.idUsuario = idUsuario;
    }

}
