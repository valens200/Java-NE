package rca.ne.java.v1.repositories;

import org.apache.commons.math3.analysis.function.Abs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rca.ne.java.v1.models.Account;
import rca.ne.java.v1.models.Customer;
import rca.ne.java.v1.models.User;

import javax.servlet.http.PushBuilder;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICustomerRepository  extends JpaRepository<Customer, UUID> {
    public boolean existsByEmail(String email);
    public Optional<Customer> findByEmail(String email);
    public Optional<Customer> findByAccount(Account account);
    public Optional<Customer> findByProfile(User profile);
}
