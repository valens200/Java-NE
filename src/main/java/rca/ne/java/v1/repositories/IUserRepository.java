package rca.ne.java.v1.repositories;
import rca.ne.java.v1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository  extends JpaRepository<User, UUID> {

    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);
    User findByEmail(String email);

}
