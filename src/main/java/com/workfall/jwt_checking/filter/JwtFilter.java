package com.workfall.jwt_checking.filter;

import com.workfall.jwt_checking.document.AppUser;
import com.workfall.jwt_checking.document.UserPrincipal;
import com.workfall.jwt_checking.repo.UserPrincipalRepo;
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
    private final UserPrincipalRepo userPrincipalRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        try {
            final String requestToken = request.getHeader("Authorization");

            if (requestToken == null || !requestToken.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = requestToken.split("Bearer ")[1];
            String email = jwtService.getUserFromToken(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                AppUser appUser = authService.findUserByEmail(email);
                UserPrincipal userPrincipal = userPrincipalRepo.findByAppUser(appUser)
                        .orElseThrow(() -> new BadCredentialsException("Not found"));
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }catch(Exception e){
            handlerExceptionResolver.resolveException(request , response , null , e);
        }
    }
}
