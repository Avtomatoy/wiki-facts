package ru.avtomaton.wikifacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.avtomaton.wikifacts.entity.Role;

/**
 * @author Anton Akkuzin
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
