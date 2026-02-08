package Gestao.de.Ingressos.controller;

import Gestao.de.Ingressos.dto.EventoRequestDTO;
import Gestao.de.Ingressos.dto.EventoResponseDTO;
import Gestao.de.Ingressos.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    public ResponseEntity<EventoResponseDTO> create(@RequestBody @Valid EventoRequestDTO data) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EventoResponseDTO novoEvento = eventoService.createEvento(data, email);
        return ResponseEntity.ok(novoEvento);
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> findAll() {
        return ResponseEntity.ok(eventoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> update(@PathVariable Long id, @RequestBody @Valid EventoRequestDTO data) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EventoResponseDTO eventoAtualizado = eventoService.updateEvento(id, data, email);
        return ResponseEntity.ok(eventoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        eventoService.deleteEvento(id, email);
        return ResponseEntity.noContent().build();
    }
}
