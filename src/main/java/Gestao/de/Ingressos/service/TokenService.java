package Gestao.de.Ingressos.service;

import Gestao.de.Ingressos.model.UsuarioEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateAccessToken(UsuarioEntity user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("eventhub-api")
                    .withSubject(user.getEmailUsuario())
                    .withClaim("id", user.getId())          // Adicionado ID
                    .withClaim("nome", user.getNomeUsuario()) // Adicionado Nome
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(genExpirationDate(30)) // 30 minutos
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String generateRefreshToken(UsuarioEntity user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("eventhub-api")
                    .withSubject(user.getEmailUsuario())
                    .withClaim("id", user.getId())          // Adicionado ID também no refresh
                    .withClaim("type", "refresh")
                    .withExpiresAt(genExpirationDate(60 * 24)) // 24 horas
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar refresh token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("eventhub-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate(long minutes) {
        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of("-03:00"));
    }
}
