package lt.codeacademy.blog_spring.repositories;

import lt.codeacademy.blog_spring.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> getRoleByName(String name);
}
