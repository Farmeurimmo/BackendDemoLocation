package fr.farmeurimmo.backenddemolocation.security;

import fr.farmeurimmo.backenddemolocation.controllers.users.User;
import fr.farmeurimmo.backenddemolocation.controllers.users.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final UserService userService;

    public ApiKeyAuthFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (request.getMethod().equals(HttpMethod.POST.name())) {
            if (request.getRequestURI().equals("/users")) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(null, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);
                return;
            }
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String apiKey = authorizationHeader.substring(BEARER_PREFIX.length());
            Optional<User> user = userService.getUserByApiKey(apiKey);

            if (user.isPresent()) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user.get(), null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized");
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
