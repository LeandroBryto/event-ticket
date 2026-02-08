package Gestao.de.Ingressos.controller;

import Gestao.de.Ingressos.dto.IngressoDetalheDTO;
import Gestao.de.Ingressos.service.IngressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {

    @Autowired
    private IngressoService ingressoService;

    @GetMapping("/meus-ingressos")
    public ResponseEntity<List<IngressoDetalheDTO>> getMeusIngressos() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<IngressoDetalheDTO> ingressos = ingressoService.listarMeusIngressos(email);
        return ResponseEntity.ok(ingressos);
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<IngressoDetalheDTO>> getIngressosPorEvento(@PathVariable Long eventoId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<IngressoDetalheDTO> ingressos = ingressoService.listarIngressosPorEvento(eventoId, email);
        return ResponseEntity.ok(ingressos);
    }
}
