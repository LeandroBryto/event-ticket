package Gestao.de.Ingressos.service;

import Gestao.de.Ingressos.dto.VendaRequestDTO;
import Gestao.de.Ingressos.dto.VendaResponseDTO;
import Gestao.de.Ingressos.enums.Role;
import Gestao.de.Ingressos.enums.StatusIngresso;
import Gestao.de.Ingressos.model.*;
import Gestao.de.Ingressos.repository.EventoRepository;
import Gestao.de.Ingressos.repository.IngressoRepository;
import Gestao.de.Ingressos.repository.UsuarioRepository;
import Gestao.de.Ingressos.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public VendaResponseDTO realizarVenda(VendaRequestDTO data, String emailComprador) {
        UsuarioEntity comprador = usuarioRepository.findByEmailUsuario(emailComprador)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (comprador.getRole() != Role.USUARIO) {
            throw new RuntimeException("Apenas compradores podem realizar compras");
        }

        EventoEntity evento = eventoRepository.findById(data.getEventoId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        if (evento.getIngressosDisponiveis() < data.getQuantidade()) {
            throw new RuntimeException("Não há ingressos suficientes para esta compra. Disponíveis: " + evento.getIngressosDisponiveis());
        }

        // Baixa no estoque
        evento.setIngressosDisponiveis(evento.getIngressosDisponiveis() - data.getQuantidade());
        eventoRepository.save(evento);

        // Cria a venda
        VendaEntity venda = new VendaEntity();
        venda.setComprador(comprador);
        venda.setDataVenda(LocalDateTime.now());
        venda.setValorTotal(evento.getPreco().multiply(new BigDecimal(data.getQuantidade())));
        
        VendaEntity savedVenda = vendaRepository.save(venda);

        // Cria os ingressos
        List<IngressoEntity> ingressos = new ArrayList<>();
        for (int i = 0; i < data.getQuantidade(); i++) {
            IngressoEntity ingresso = new IngressoEntity();
            ingresso.setEvento(evento);
            ingresso.setUSUARIO(comprador);
            ingresso.setVenda(savedVenda);
            ingresso.setPreco(evento.getPreco());
            ingresso.setLote("Lote Único");
            ingresso.setDataCompra(LocalDateTime.now());
            ingresso.setStatus(StatusIngresso.ATIVO);
            ingresso.setCodigo(UUID.randomUUID().toString());
            ingressos.add(ingresso);
        }
        ingressoRepository.saveAll(ingressos);
        
        savedVenda.setIngressos(ingressos);

        return new VendaResponseDTO(
                savedVenda.getId(),
                evento.getNome(),
                savedVenda.getDataVenda(),
                savedVenda.getValorTotal(),
                ingressos.stream().map(IngressoEntity::getCodigo).collect(Collectors.toList())
        );
    }
}
