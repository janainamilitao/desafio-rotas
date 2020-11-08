package br.radixeng.repository;

import br.radixeng.model.Graph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Janaina Militão
 */
public interface GraphRepository extends JpaRepository<Graph, Long> {
    Optional<Graph> findById(Long idGraph);
}
