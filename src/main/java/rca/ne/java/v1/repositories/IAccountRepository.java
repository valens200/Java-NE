package rca.ne.java.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rca.ne.java.v1.models.Account;
import rca.ne.java.v1.models.Customer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAccountRepository extends JpaRepository<Account, UUID> {
    public boolean existsByOwner(Customer customer);
    public Optional<Account> findByOwner(Customer customer);
}
