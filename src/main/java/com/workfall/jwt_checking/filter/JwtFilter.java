package com.workfall.jwt_checking.filter;

import com.workfall.jwt_checking.document.AppUser;
import com.workfall.jwt_checking.document.Tokens;
import com.workfall.jwt_checking.document.UserPrincipal;
import com.workfall.jwt_checking.repo.TokenRepo;
import com.workfall.jwt_checking.service.AuthService;
import com.workfall.jwt_checking.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService ;
    private final AuthService authService ;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver ;
    private final TokenRepo tokenRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        try {
            final String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Missing or invalid Authorization header.");
                return;
            }

            String token = authorizationHeader.substring(7);

            String email;
            try {
                email = jwtService.getUserFromToken(token);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid JWT token.");
                return;
            }

            if (isTokenRevoked(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("JWT token has been revoked.");
                return;
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                AppUser appUser = authService.findUserByEmail(email);
                UserPrincipal userPrincipal = new UserPrincipal(appUser);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

    private boolean isTokenRevoked(String token) {
        Tokens tokenEntity = tokenRepo.findByJwtToken(token);
        return tokenEntity == null || tokenEntity.isRevoked();
    }

}
