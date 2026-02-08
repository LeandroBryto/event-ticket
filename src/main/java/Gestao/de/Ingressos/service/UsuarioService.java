package Gestao.de.Ingressos.service;

import Gestao.de.Ingressos.dto.LoginRequestDTO;
import Gestao.de.Ingressos.dto.LoginResponseDTO;
import Gestao.de.Ingressos.dto.RegisterRequestDTO;
import Gestao.de.Ingressos.model.UsuarioEntity;
import Gestao.de.Ingressos.repository.UsuarioRepository;
import Gestao.de.Ingressos.util.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final EmailService emailService;

    public LoginResponseDTO register(RegisterRequestDTO data) {
        if (repository.findByEmailUsuario(data.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        UsuarioEntity newUser = new UsuarioEntity();
        newUser.setNomeUsuario(data.getNome());
        newUser.setEmailUsuario(data.getEmail());
        newUser.setCpf(data.getCpf());
        newUser.setSenhaUsuario(passwordEncoder.encode(data.getPassword()));
        newUser.setRole(data.getRole());

        UsuarioEntity savedUser = repository.save(newUser);
        
        // Envia email de boas-vindas
        emailService.sendWelcomeEmail(savedUser.getEmailUsuario(), savedUser.getNomeUsuario());

        return generateLoginResponse(savedUser);
    }

    public LoginResponseDTO login(LoginRequestDTO data) {
        UsuarioEntity user = repository.findByEmailUsuario(data.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(data.getPassword(), user.getSenhaUsuario())) {
            throw new RuntimeException("Senha incorreta");
        }

        return generateLoginResponse(user);
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        String email = tokenService.validateToken(refreshToken);
        if (email.isEmpty()) {
            throw new RuntimeException("Refresh Token inválido ou expirado");
        }

        UsuarioEntity user = repository.findByEmailUsuario(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return generateLoginResponse(user);
    }

    public void forgotPassword(String email) {
        UsuarioEntity user = repository.findByEmailUsuario(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = UUID.randomUUID().toString();
        
        // Envia email de recuperação
        emailService.sendPasswordRecoveryEmail(user.getEmailUsuario(), token);
    }

    private LoginResponseDTO generateLoginResponse(UsuarioEntity user) {
        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        return new LoginResponseDTO(accessToken, refreshToken);
    }
}
