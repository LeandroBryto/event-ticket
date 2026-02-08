package Gestao.de.Ingressos.repository;

import Gestao.de.Ingressos.dto.IngressoDetalheDTO;
import Gestao.de.Ingressos.model.EventoEntity;
import Gestao.de.Ingressos.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, Long> {
    List<EventoEntity> findByOrganizador(UsuarioEntity organizador);
    List<EventoEntity> findByDataEventoAfter(LocalDateTime data);

    interface IngressoRepositoryCustom {
        List<IngressoDetalheDTO> findIngressosByComprador(Long compradorId);
        List<IngressoDetalheDTO> findIngressosByEvento(Long eventoId);
    }
}
