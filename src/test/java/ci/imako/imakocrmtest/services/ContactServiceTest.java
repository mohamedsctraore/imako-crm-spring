package ci.imako.imakocrmtest.services;

import ci.imako.imakocrmtest.domain.Commande;
import ci.imako.imakocrmtest.domain.Contact;

import ci.imako.imakocrmtest.domain.RendezVous;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private RendezVousService rendezVousService;

    @Autowired
    private CommandeService commandeService;

    private Contact contact;

    @Before
    public void setup() {
        contact = new Contact();
        contact.setNom("Mohamed Traore");
        contact.setEmail("mohamed.traore@imako.digital");
        contact.setTelephone("08-35-35-94");
        contactService.save(contact);
    }

    @Test
    public void suspectTest() {
        assertEquals(Contact.Categorie.SUSPECT, contact.getCategorie());
    }

    @Test
    public void prospectTest() {
        RendezVous rendezVous = new RendezVous();
        rendezVous.setDateRendezVous(new Date());
        rendezVous.setNoteRendezVous("Mon Rendez vous avec Mohamed Traore");

        RendezVous rendezVousSaved = rendezVousService.save(rendezVous);

        contact.addRendezVous(rendezVousSaved);
        contact = contactService.manageStatusContact(contact);

        assertEquals(Contact.Categorie.PROSPECT, contact.getCategorie());

        contact.removeRendezVous(rendezVousSaved);
        contact = contactService.manageStatusContact(contact);

        assertEquals(Contact.Categorie.SUSPECT, contact.getCategorie());
    }

    @Test
    public void clientTest() {
        Commande commande = new Commande();
        commande.setNoteCommande("Commande de 10 unités du produit A");

        Commande commandeSaved = commandeService.save(commande);

        contact.addCommande(commandeSaved);
        contact = contactService.manageStatusContact(contact);

        assertEquals(Contact.Categorie.CLIENT, contact.getCategorie());

        contact.removeCommande(commandeSaved);
        contact = contactService.manageStatusContact(contact);

        assertEquals(Contact.Categorie.SUSPECT, contact.getCategorie());
    }

    @Test
    public void vipTest() {
        for (int i = 0; i < 10; i++) {
            Commande commande = new Commande();
            commande.setNoteCommande("Commande numero " + i);
            contact.addCommande(commandeService.save(commande));
        }

        contact = contactService.manageStatusContact(contact);

        assertEquals(Contact.Categorie.VIP, contact.getCategorie());
    }


}
