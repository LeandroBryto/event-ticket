package Gestao.de.Ingressos.config;

import Gestao.de.Ingressos.model.UsuarioEntity;
import Gestao.de.Ingressos.repository.UsuarioRepository;
import Gestao.de.Ingressos.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        
        if (token != null) {
            var login = tokenService.validateToken(token);

            if (!login.isEmpty()) {
                Optional<UsuarioEntity> userOptional = usuarioRepository.findByEmailUsuario(login);

                if (userOptional.isPresent()) {
                    UsuarioEntity user = userOptional.get();
                    var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
                    
                    var authentication = new UsernamePasswordAuthenticationToken(user.getEmailUsuario(), null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {

                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Usuário inválido ou não encontrado\"}");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
