package br.radixeng.repository;

import br.radixeng.model.Graph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GraphRepository extends JpaRepository<Graph, Long> {
    Optional<Graph> findById(Long idGraph);
}
