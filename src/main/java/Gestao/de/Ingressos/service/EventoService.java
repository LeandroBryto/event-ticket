package Gestao.de.Ingressos.service;

import Gestao.de.Ingressos.dto.EventoRequestDTO;
import Gestao.de.Ingressos.dto.EventoResponseDTO;
import Gestao.de.Ingressos.enums.Role;
import Gestao.de.Ingressos.model.EventoEntity;
import Gestao.de.Ingressos.model.UsuarioEntity;
import Gestao.de.Ingressos.repository.EventoRepository;
import Gestao.de.Ingressos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public EventoResponseDTO createEvento(EventoRequestDTO data, String emailOrganizador) {
        UsuarioEntity organizador = usuarioRepository.findByEmailUsuario(emailOrganizador)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (organizador.getRole() != Role.ORGANIZADOR) {
            throw new RuntimeException("Apenas organizadores podem criar eventos");
        }

        EventoEntity evento = new EventoEntity();
        evento.setNome(data.getNome());
        evento.setDescricao(data.getDescricao());
        evento.setDataEvento(data.getDataEvento());
        evento.setLocal(data.getLocal());
        evento.setCapacidade(data.getCapacidade());
        evento.setIngressosDisponiveis(data.getCapacidade());
        evento.setPreco(data.getPreco());
        evento.setOrganizador(organizador);

        EventoEntity savedEvento = eventoRepository.save(evento);
        return convertToDTO(savedEvento);
    }

    public List<EventoResponseDTO> findAll() {
        return eventoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EventoResponseDTO findById(Long id) {
        EventoEntity evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        return convertToDTO(evento);
    }

    public EventoResponseDTO updateEvento(Long id, EventoRequestDTO data, String emailSolicitante) {
        EventoEntity evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        UsuarioEntity solicitante = usuarioRepository.findByEmailUsuario(emailSolicitante)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!evento.getOrganizador().getId().equals(solicitante.getId()) && solicitante.getRole() != Role.MASTER) {
            throw new RuntimeException("Você não tem permissão para editar este evento");
        }

        if (!evento.getCapacidade().equals(data.getCapacidade())) {
            int diferenca = data.getCapacidade() - evento.getCapacidade();
            int novosDisponiveis = evento.getIngressosDisponiveis() + diferenca;
            
            if (novosDisponiveis < 0) {
                throw new RuntimeException("A nova capacidade é menor do que a quantidade de ingressos já vendidos");
            }
            evento.setIngressosDisponiveis(novosDisponiveis);
            evento.setCapacidade(data.getCapacidade());
        }

        evento.setNome(data.getNome());
        evento.setDescricao(data.getDescricao());
        evento.setDataEvento(data.getDataEvento());
        evento.setLocal(data.getLocal());
        evento.setPreco(data.getPreco());

        EventoEntity updatedEvento = eventoRepository.save(evento);
        return convertToDTO(updatedEvento);
    }
    
    public void deleteEvento(Long id, String emailSolicitante) {
        EventoEntity evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        
        UsuarioEntity solicitante = usuarioRepository.findByEmailUsuario(emailSolicitante)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!evento.getOrganizador().getId().equals(solicitante.getId()) && solicitante.getRole() != Role.MASTER) {
            throw new RuntimeException("Você não tem permissão para deletar este evento");
        }

        eventoRepository.delete(evento);
    }

    private EventoResponseDTO convertToDTO(EventoEntity evento) {
        return new EventoResponseDTO(
                evento.getId(),
                evento.getNome(),
                evento.getDescricao(),
                evento.getDataEvento(),
                evento.getLocal(),
                evento.getCapacidade(),
                evento.getIngressosDisponiveis(),
                evento.getPreco(),
                evento.getOrganizador().getNomeUsuario()
        );
    }
}
