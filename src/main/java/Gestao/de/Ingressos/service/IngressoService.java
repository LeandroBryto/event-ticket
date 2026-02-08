package Gestao.de.Ingressos.service;

import Gestao.de.Ingressos.dto.IngressoDetalheDTO;
import Gestao.de.Ingressos.enums.Role;
import Gestao.de.Ingressos.model.UsuarioEntity;
import Gestao.de.Ingressos.repository.IngressoRepository;
import Gestao.de.Ingressos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<IngressoDetalheDTO> listarMeusIngressos(String emailUsuario) {
        UsuarioEntity usuario = usuarioRepository.findByEmailUsuario(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return ingressoRepository.findIngressosByComprador(usuario.getId());
    }

    public List<IngressoDetalheDTO> listarIngressosPorEvento(Long eventoId, String emailOrganizador) {
        UsuarioEntity organizador = usuarioRepository.findByEmailUsuario(emailOrganizador)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (organizador.getRole() != Role.ORGANIZADOR && organizador.getRole() != Role.MASTER) {
            throw new RuntimeException("Apenas organizadores podem ver os ingressos de um evento.");
        }

        return ingressoRepository.findIngressosByEvento(eventoId);
    }
}
