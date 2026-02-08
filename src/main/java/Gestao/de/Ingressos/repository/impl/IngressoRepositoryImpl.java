package Gestao.de.Ingressos.repository.impl;

import Gestao.de.Ingressos.dto.IngressoDetalheDTO;
import Gestao.de.Ingressos.repository.EventoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IngressoRepositoryImpl implements EventoRepository.IngressoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<IngressoDetalheDTO> findIngressosByComprador(Long compradorId) {
        String sql = getBaseQuery() + " WHERE i.cod_comprador = :compradorId";
        Query query = entityManager.createNativeQuery(sql, "IngressoDetalheDTOMapping");
        query.setParameter("compradorId", compradorId);
        return query.getResultList();
    }

    @Override
    public List<IngressoDetalheDTO> findIngressosByEvento(Long eventoId) {
        String sql = getBaseQuery() + " WHERE i.cod_evento = :eventoId";
        Query query = entityManager.createNativeQuery(sql, "IngressoDetalheDTOMapping");
        query.setParameter("eventoId", eventoId);
        return query.getResultList();
    }

    private String getBaseQuery() {
        return """
                SELECT
                    i.cod_ingresso as id,
                    i.cod_validacao as codigoValidacao,
                    e.nome_evento as nomeEvento,
                    i.data_compra as dataCompra,
                    i.val_preco_pago as preco,
                    i.status_ingresso as status,
                    u.nome_usuario as nomeComprador
                FROM
                    tbl_ingresso i
                INNER JOIN
                    tbl_evento e ON i.cod_evento = e.cod_evento
                INNER JOIN
                    tbl_usuario u ON i.cod_comprador = u.cod_usuario
               """;
    }
}
