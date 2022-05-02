package ru.avtomaton.wikifacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.avtomaton.wikifacts.entity.Category;
import ru.avtomaton.wikifacts.entity.Fact;
import ru.avtomaton.wikifacts.entity.User;

import java.util.List;
import java.util.Set;

/**
 * @author Anton Akkuzin
 */
public interface FactRepository extends JpaRepository<Fact, Long> {

    @Query(value = "SELECT * FROM public.t_fact WHERE is_deleted = 'false' AND status = 'VERIFIED' ORDER BY rating DESC  LIMIT 1 OFFSET ?1", nativeQuery = true)
    Fact findOneByRatingDescWithOffsetVerified(long offset);

    @Query(value = "SELECT * FROM public.t_fact WHERE is_deleted = 'false' ORDER BY rating DESC  LIMIT 1 OFFSET ?1", nativeQuery = true)
    Fact findOneByRatingDescWithOffset(long offset);

    @Query(value = "SELECT * FROM public.t_fact WHERE fact_text ~* ?2 AND is_deleted = 'false' AND status = 'VERIFIED' LIMIT 1 OFFSET ?1", nativeQuery = true)
    Fact findOneByKeywordsVerified(long offset, String sqlArrayOfKeywords);

    @Query(value = "SELECT * FROM public.t_fact WHERE fact_text ~* ?2 AND is_deleted = 'false' LIMIT 1 OFFSET ?1", nativeQuery = true)
    Fact findOneByKeywords(long offset, String sqlArrayOfKeywords);

    @Query(value = "select * from public.t_fact where status = 'VERIFIED' order by random() limit 1", nativeQuery = true)
    Fact findRandomVerified();

    @Query(value = "select * from public.t_fact order by random() limit 1", nativeQuery = true)
    Fact findRandom();

    @Query(value = "SELECT * FROM public.t_fact where is_deleted = 'true'", nativeQuery = true)
    List<Fact> findAllWithDeletedMark();

    List<Fact> findAllByCategoriesIn(Set<Category> categories);
}
