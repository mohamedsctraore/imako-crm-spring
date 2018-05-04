package ci.imako.imakocrmtest.services.impl;

import ci.imako.imakocrmtest.domain.Contact;
import ci.imako.imakocrmtest.exceptions.ResourceNotFoundException;
import ci.imako.imakocrmtest.repositories.ContactRepository;
import ci.imako.imakocrmtest.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        Optional<Contact> contact = Optional.ofNullable(contactRepository.findOne(id));
        if (contact.isPresent()) {
            return contact.get();
        } else {
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }

    @Override
    public Contact save(Contact contact) {
        contact.setCategorie(Contact.Categorie.SUSPECT);
        return contactRepository.save(contact);
    }

    @Override
    public void update(Contact contact) {

    }

    @Override
    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Contact> contact = Optional.ofNullable(contactRepository.findOne(id));
        if (contact.isPresent()) {
            contactRepository.delete(id);
        } else {
            throw new ResourceNotFoundException();
        }

    }
}
