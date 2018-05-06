package ci.imako.imakocrmtest.services;

import ci.imako.imakocrmtest.domain.Contact;

import java.util.List;

public interface ContactService extends AbstractService<Contact, Long> {
    Contact manageStatusContact(Contact contact);
    List<Contact> findAllNomLike(String motCle);
}
