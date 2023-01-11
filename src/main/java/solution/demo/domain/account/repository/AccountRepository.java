package solution.demo.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solution.demo.domain.account.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByUserId(String userId);

    Optional<Account> findByUserId(String userId);
}
