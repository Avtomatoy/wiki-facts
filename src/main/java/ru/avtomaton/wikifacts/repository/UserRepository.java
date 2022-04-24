package ru.avtomaton.wikifacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.avtomaton.wikifacts.entity.User;

/**
 * @author Anton Akkuzin
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
