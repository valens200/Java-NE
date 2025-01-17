package rca.ne.java.v1.repositories;
import rca.ne.java.v1.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IRoleRepository  extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String  name);
    boolean existsByRoleName(String name);
}
