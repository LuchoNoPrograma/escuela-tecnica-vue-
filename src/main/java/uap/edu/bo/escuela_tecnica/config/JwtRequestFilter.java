package uap.edu.bo.escuela_tecnica.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import uap.edu.bo.escuela_tecnica.security.JwtSecurityConfigTokenService;


/**
 * Filter for authorizing requests based on the "Authorization: Bearer ..." header. When
 * a valid JWT has been found, load the user details from the database and set the
 * authenticated principal for the duration of this request.
 */
@Log4j2
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtSecurityConfigTokenService jwtSecurityConfigTokenService;

    public JwtRequestFilter(final UserDetailsService userDetailsService,
            final JwtSecurityConfigTokenService jwtSecurityConfigTokenService) {
        this.userDetailsService = userDetailsService;
        this.jwtSecurityConfigTokenService = jwtSecurityConfigTokenService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain chain) throws IOException,
            ServletException {
        // look for Bearer auth header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        final DecodedJWT jwt = jwtSecurityConfigTokenService.validateToken(token);
        if (jwt == null || jwt.getSubject() == null) {
            log.warn("Autenticación fallida: JWT inválido o expirado para request [{} {}] desde IP {}",
                request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
            chain.doFilter(request, response);
            return;
        }

        final UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(jwt.getSubject());
        } catch (final UsernameNotFoundException userNotFoundEx) {
            log.warn("Autenticación fallida: Usuario [{}] no encontrado para JWT válido en [{} {}] desde IP {}",
                jwt.getSubject(), request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
            chain.doFilter(request, response);
            return;
        }

        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // set user details on spring security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Autenticación exitosa: Usuario [{}] autenticado para [{} {}] desde IP {}",
            userDetails.getUsername(), request.getMethod(), request.getRequestURI(), request.getRemoteAddr());

        // continue with authenticated user
        chain.doFilter(request, response);
    }

}
