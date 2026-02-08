package Gestao.de.Ingressos.controller;

import Gestao.de.Ingressos.dto.VendaRequestDTO;
import Gestao.de.Ingressos.dto.VendaResponseDTO;
import Gestao.de.Ingressos.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaResponseDTO> realizarVenda(@RequestBody @Valid VendaRequestDTO data) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        VendaResponseDTO response = vendaService.realizarVenda(data, email);
        return ResponseEntity.ok(response);
    }
}
