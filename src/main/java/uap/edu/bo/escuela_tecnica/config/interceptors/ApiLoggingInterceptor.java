package uap.edu.bo.escuela_tecnica.config.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class ApiLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            String controllerName = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            String httpMethod = request.getMethod();
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
            String username = (SecurityContextHolder.getContext().getAuthentication() != null)
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : "ANÓNIMO";
            String fullUrl = (queryString != null) ? (uri + "?" + queryString) : uri;

            log.info("\u001B[34mUsuario: [{}] | Api: [{}] | [{}] {}\u001B[0m",
                username,
                controllerName,
                methodName,
                fullUrl
            );
        } else {
            // Por si el handler no es HandlerMethod
            log.info("\u001B[34m[{}] {} | Usuario: [{}]\u001B[0m",
                request.getMethod(),
                request.getRequestURI(),
                (SecurityContextHolder.getContext().getAuthentication() != null)
                    ? SecurityContextHolder.getContext().getAuthentication().getName()
                    : "ANÓNIMO"
            );
        }
        return true;
    }
}
