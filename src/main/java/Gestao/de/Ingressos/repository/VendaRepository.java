package Gestao.de.Ingressos.repository;

import Gestao.de.Ingressos.model.UsuarioEntity;
import Gestao.de.Ingressos.model.VendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<VendaEntity, Long> {
    List<VendaEntity> findByComprador(UsuarioEntity comprador);
}
