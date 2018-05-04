package ci.imako.imakocrmtest.repositories;

import ci.imako.imakocrmtest.domain.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountUserRepository extends JpaRepository<AccountUser, Long> {
    Optional<AccountUser> findByLogin(String login);
}
