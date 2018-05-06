package ci.imako.imakocrmtest.services.impl;

import ci.imako.imakocrmtest.domain.Contact;
import ci.imako.imakocrmtest.exceptions.ResourceNotFoundException;
import ci.imako.imakocrmtest.repositories.ContactRepository;
import ci.imako.imakocrmtest.services.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {
    private static final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> findAll() {
        log.info("APPELLE METHODE FINDALL CONTACTS");
        log.debug("IL EXISTE " + contactRepository.findAll().size() + " CONTACTS EN BDD");
        return contactRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        log.info("APPELLE METHODE FINDBYID DE CONTACT");
        Optional<Contact> contact = Optional.ofNullable(contactRepository.findOne(id));
        if (contact.isPresent()) {
            log.debug("ID CONTACT : " + contact.get().getId());
            log.debug("NUMERO CONTACT : " + contact.get().getNom());
            return contact.get();
        } else {
            log.error("LE CONTACT ID NUMERO : " + id + " N'EXISTE PAS");
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }

    @Override
    public Contact save(Contact contact) {
        log.info("APPELLE DE LA METHODE SAVE DE CONTACT");
        if (contact.getId() == null) {
            log.debug("ID NULL CONTACT : " + contact.getId());
            contact.setCategorie(Contact.Categorie.SUSPECT);
        }
        return contactRepository.save(contact);
    }

    @Override
    public void delete(Contact contact) {
        log.info("APPELLE DE LA METHODE DELETE OBJET CONTACT");
        log.debug("ID CONTACT SUPPRIME : " + contact.getId());
        log.debug("NOM CONTACT SUPPRIME : " + contact.getNom());

        contactRepository.delete(contact);
    }

    @Override
    public void deleteById(Long id) {
        log.info("APPELLE DE LA METHODE DELETEBYID DE CONTACT");
        Optional<Contact> contact = Optional.ofNullable(contactRepository.findOne(id));
        if (contact.isPresent()) {
            contactRepository.delete(id);
            log.info("SUPPRESSION CONTACT ID : " + id);
        } else {
            log.error("CONTACT ID : " + id + " N'EXISTE PAS");
            throw new ResourceNotFoundException();
        }

    }

    @Override
    public Contact manageStatusContact(Contact contact) {
        log.info("APPELLE DE LA METHODE PERMETTANT DE GERER LE STATUS DU CONTACT");

        int taille_rendezvous = contact.getRendezVousList().size();
        int taille_commande = contact.getCommandes().size();

        log.debug("TAILLE DU TABLEAU DE RENDEZ VOUS : " + taille_rendezvous);
        log.debug("TAILLE DU TABLEAU DE COMMANDE : " + taille_commande);

        Contact.Categorie categorie = Contact.Categorie.SUSPECT;

        if (taille_rendezvous == 0) {
            if (taille_commande == 0) {
                categorie = Contact.Categorie.SUSPECT;
            } else if (taille_commande >= 10) {
                categorie = Contact.Categorie.VIP;
            } else {
                categorie = Contact.Categorie.CLIENT;
            }
        } else {
            if (taille_commande == 0) {
                categorie = Contact.Categorie.PROSPECT;
            } else {
                if (taille_commande >= 10) {
                    categorie = Contact.Categorie.VIP;
                } else {
                    categorie = Contact.Categorie.CLIENT;
                }
            }
        }

        log.debug("NOUVELLE CATEGORIE DU CONTACT : " + categorie);

        contact.setCategorie(categorie);
        log.debug("CATEGORIE DU CONTACT : " + contact.getCategorie());

        log.info("FIN DE LA METHODE ET NOUVEAU STATUS POUR LE CONTACT");

        return contact;
    }
}
