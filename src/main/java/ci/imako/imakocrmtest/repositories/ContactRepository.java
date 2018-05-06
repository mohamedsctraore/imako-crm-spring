package ci.imako.imakocrmtest.repositories;

import ci.imako.imakocrmtest.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("select c from Contact c where c.nom like %?1%")
    List<Contact> findAllByNomLike(String motCle);

    Optional<Contact> findByEmail(String email);
    Optional<Contact> findByTelephone(String telephone);
}
