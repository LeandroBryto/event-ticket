package Gestao.de.Ingressos.controller;

import Gestao.de.Ingressos.dto.LoginRequestDTO;
import Gestao.de.Ingressos.dto.LoginResponseDTO;
import Gestao.de.Ingressos.dto.RegisterRequestDTO;
import Gestao.de.Ingressos.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        LoginResponseDTO response = usuarioService.login(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody @Valid RegisterRequestDTO data) {
        LoginResponseDTO response = usuarioService.register(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestBody Map<String, String> payload) {
        String refreshToken = payload.get("refreshToken");
        LoginResponseDTO response = usuarioService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        usuarioService.forgotPassword(email);
        return ResponseEntity.ok().build();
    }
}
