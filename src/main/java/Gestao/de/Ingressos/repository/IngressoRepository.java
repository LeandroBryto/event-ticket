package Gestao.de.Ingressos.repository;

import Gestao.de.Ingressos.model.IngressoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngressoRepository extends JpaRepository<IngressoEntity, Long>, EventoRepository.IngressoRepositoryCustom {
    Optional<IngressoEntity> findByCodigo(String codigo);
}
