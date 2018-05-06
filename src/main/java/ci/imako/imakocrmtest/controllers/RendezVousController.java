package ci.imako.imakocrmtest.controllers;

import ci.imako.imakocrmtest.domain.Contact;
import ci.imako.imakocrmtest.domain.RendezVous;
import ci.imako.imakocrmtest.services.ContactService;
import ci.imako.imakocrmtest.services.RendezVousService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/dashboard")
public class RendezVousController {

    private static final Logger log = LoggerFactory.getLogger(RendezVousController.class);

    private RendezVousService rendezVousService;
    private ContactService contactService;

    @Autowired
    public RendezVousController(RendezVousService rendezVousService,
                                ContactService contactService) {
        this.rendezVousService = rendezVousService;
        this.contactService = contactService;
    }

    @GetMapping("/rendez-vous/{id}/new")
    public String newRendezVous(Model model, @PathVariable Long id) {
        log.info("DEBUT AFFICHAGE FORMULAIRE PRISE DE RENDEZVOUS");
        log.debug("ID CONTACT : " + id);

        model.addAttribute("rendezvous", new RendezVous());
        model.addAttribute("id_contact_rendezvous", id);

        log.info("AFFICHAGE DU FORMULAIRE DE RENDEZVOUS");
        return "rendezvous/form";
    }

    @GetMapping("/rendez-vous/{id}/show")
    public String showRendezVous(Model model, @PathVariable Long id) {
        log.info("DEBUT AFFICHAGE D'UN RENDEZ VOUS");

        RendezVous rendezVous = rendezVousService.findById(id);

        model.addAttribute("rendezvous", rendezVous);

        log.info("AFFICHAGE DU RENDEZ VOUS NUMERO : " + rendezVous.getId().toString());

        return "rendezvous/show";
    }

    @GetMapping("/rendez-vous/{id}/update")
    public String updateRendezVous(Model model,
                                   @PathVariable Long id) {
        log.info("DEBUT AFFICHAGE FORMULAIRE PRISE DE RENDEZ VOUS");
        log.debug("ID RENDEZ VOUS : " + id);

        RendezVous rendezVous = rendezVousService.findById(id);
        log.debug("ID RENDEZVOUS A MODIFIE : " + rendezVous.getId());
        log.debug("ID CONTACT DU RENDEZ VOUS : " + rendezVous.getContact().getId());

        model.addAttribute("rendezvous", rendezVous);

        log.info("AFFICHAGE DU FORMULAIRE DE MODIFICATION");
        return "rendezvous/form";
    }

    @GetMapping("/rendez-vous/{id}/delete")
    public String deleteRendezVous(@PathVariable Long id) {
        log.info("INVOCATION METHODE DE SUPPRESSION");

        RendezVous rendezVous = rendezVousService.findById(id);
        Contact contact = rendezVous.getContact();

        contact.removeRendezVous(rendezVous);
        log.debug("TAILLE DE LA LISTE DES RENDEZ VOUS");

        log.debug(contact.getId().toString());
        log.debug(contact.getNom());
        log.debug(contact.getEmail());
        log.debug(contact.getTelephone());

        Long id_contact = contact.getId();
        rendezVousService.delete(rendezVous);

        log.info("SUPPRESSION RENDEZ VOUS NUMERO : " + id);

        contact = contactService.manageStatusContact(contact);
        log.debug("STATUS CATEGORIE CONTACT : " + contact.getCategorie());

        contactService.save(contact);
        log.info("MISE A JOUR DU CONTACT");

        return "redirect:/dashboard/contact/" + id_contact + "/show";
    }

    @PostMapping("/rendezvous")
    public String saveOrUpdateRendezVous(@Valid @ModelAttribute("rendezvous") RendezVous rendezVous,
                                         BindingResult result,
                                         @ModelAttribute("id_contact") Long id) {
        log.info("DEBUT VALIDATION FORMULAIRE SAVE OR UPDATE");

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "rendezvous/form";
        }

        Contact contact = contactService.findById(id);

        log.debug("NOM CONTACT : " + contact.getNom());
        log.debug("EMAIL CONTACT : " + contact.getEmail());
        log.debug("CATEGORIE CONTACT : " + contact.getCategorie().name());

        rendezVous.setContact(contact);

        RendezVous rendezVousSaved = rendezVousService.save(rendezVous);

        log.info("ENREGISTREMENT RENDEZVOUS ID : " + rendezVousSaved.getId());

        contact.addRendezVous(rendezVousSaved);
        log.debug("Taille de la liste des rendez vous : " + contact.getRendezVousList().size());

        contact = contactService.manageStatusContact(contact);
        log.debug("STATUS CATEGORIE : " + contact.getCategorie());

        contactService.save(contact);
        log.info("MISE A JOUR DU CONTACT");

        return "redirect:/dashboard/contact/" + contact.getId() + "/show";
    }
}
