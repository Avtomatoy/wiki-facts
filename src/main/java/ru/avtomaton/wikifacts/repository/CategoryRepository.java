package ru.avtomaton.wikifacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.avtomaton.wikifacts.entity.Category;

/**
 * @author Anton Akkuzin
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
