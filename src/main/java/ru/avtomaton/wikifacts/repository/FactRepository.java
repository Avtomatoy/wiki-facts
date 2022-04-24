package ru.avtomaton.wikifacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.avtomaton.wikifacts.entity.Fact;

/**
 * @author Anton Akkuzin
 */
public interface FactRepository extends JpaRepository<Fact, Long> {
}
