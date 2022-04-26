package ru.avtomaton.wikifacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.avtomaton.wikifacts.entity.Fact;

/**
 * @author Anton Akkuzin
 */
public interface FactRepository extends JpaRepository<Fact, Long> {

    @Query(value = "SELECT id, author, fact_text, publication_date, rating, status FROM public.t_fact ORDER BY rating DESC LIMIT 1 OFFSET ?1", nativeQuery = true)
    Fact findOneByRatingDescWithOffset(long offset);
}
