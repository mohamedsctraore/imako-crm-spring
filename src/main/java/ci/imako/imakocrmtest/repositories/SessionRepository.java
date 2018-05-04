package ci.imako.imakocrmtest.repositories;

import ci.imako.imakocrmtest.domain.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Sessions, Long> {
}
