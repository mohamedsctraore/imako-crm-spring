package ci.imako.imakocrmtest.controllers;

import ci.imako.imakocrmtest.domain.Commande;
import ci.imako.imakocrmtest.domain.Contact;
import ci.imako.imakocrmtest.domain.RendezVous;
import ci.imako.imakocrmtest.services.CommandeService;
import ci.imako.imakocrmtest.services.ContactService;
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
public class CommandeController {
    private static final Logger log = LoggerFactory.getLogger(CommandeController.class);

    private CommandeService commandeService;
    private ContactService contactService;

    @Autowired
    public CommandeController(CommandeService commandeService, ContactService contactService) {
        this.commandeService = commandeService;
        this.contactService = contactService;
    }

    @GetMapping("/commande/{id}/new")
    public String newCommande(Model model, @PathVariable Long id) {
        log.info("AFFICHAGE DU FORMULAIRE DE CREATION D'UNE COMMANDE");

        model.addAttribute("rendezvous", new RendezVous());
        model.addAttribute("id_contact_commande", id);

        log.info("AFFICHAGE DU FORMULAIRE");
        return "commandes/form";
    }

    @GetMapping("/commande/{id}/show")
    public String showCommande(Model model, @PathVariable Long id) {
        log.info("DEBUT METHODE POUR AFFICHER UNE COMMANDE");
        log.debug("ID COMMANDE A AFFICHER : " + id);

        Commande commande = commandeService.findById(id);
        log.debug("ID COMMANDE : " + commande.getId().toString());
        log.debug("NOTE COMMANDE : " + commande.getNoteCommande());

        model.addAttribute("commande", commande);

        log.info("AFFICHAGE DE LA PAGE POUR VOIR UNE COMMANDE");
        return "commandes/show";
    }

    @GetMapping("/commande/{id}/update")
    public String updateCommande(Model model, Long id) {
        log.info("DEBUT AFFICHAGE PAGE DE MODIFICATION COMMANDE");
        log.debug("ID COMMANDE A MODIFIER : " + id);

        Commande commande = commandeService.findById(id);
        log.debug("ID COMMANDE : " + commande.getId().toString());
        log.debug("NOTE DE COMMANDE : " + commande.getNoteCommande());

        model.addAttribute("commande", commande);

        log.info("AFFICHAGE DE LA PAGE DE MODIFICATION D'UNE COMMANDE");
        return "commandes/form";
    }

    @GetMapping("/commande/{id}/delete")
    public String deleteCommande(@PathVariable Long id) {
        log.info("INVOCATION SUPPRESSION D'UNE COMMANDE");
        log.debug("ID COMMANDE A SUPPRIMER : " + id);

        Commande commande = commandeService.findById(id);
        log.debug("ID COMMANDE : " + commande.getId().toString());
        log.debug("NOTE DE COMMANDE : " + commande.getNoteCommande());

        Contact contact = commande.getContact();

        contact.removeCommande(commande);
        log.debug("TAILLE DE LA LISTE DES COMMANDES : " + contact.getCommandes().size());

        Long id_contact = contact.getId();
        log.debug(contact.getId().toString());
        log.debug(contact.getNom());
        log.debug(contact.getEmail());
        log.debug(contact.getTelephone());

        commandeService.delete(commande);
        log.info("SUPPRESSION DE LA COMMANDE NUMERO : " + id);

        contact = contactService.manageStatusContact(contact);
        log.debug("STATUS CATEGORIE CONTACT : " + contact.getCategorie());

        contactService.save(contact);
        log.info("MISE A JOUR DU CONTACT");

        return "redirect:/dashboard/contact/" + id_contact + "/show";
    }

    @PostMapping("/commande")
    public String saveOrUpdate(@Valid @ModelAttribute("commande") Commande commande,
                               BindingResult result,
                               @ModelAttribute("id_contact_commande") Long id) {

        log.info("INVOCATION VALIDATION DE FORMULAIRE DE COMMANDE");
        log.debug("ID CONTACT LIE A LA COMMANDE : "  + id);

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "commandes/form";
        }

        Contact contact = contactService.findById(id);
        log.debug("ID CONTACT : " + contact.getId().toString());
        log.debug("NOM CONTACT : " + contact.getNom());
        log.debug("EMAIL CONTACT : " + contact.getEmail());
        log.debug("TELEPHONE CONTACT : " + contact.getTelephone());

        commande.setContact(contact);
        Commande commandeSaved = commandeService.save(commande);
        log.info("ENREGISTREMENT DE LA COMMANDE NUMERO : " + commandeSaved.getId());

        contact.addCommande(commandeSaved);
        log.debug("Taille de la liste des commandes : " + contact.getCommandes().size());

        contact = contactService.manageStatusContact(contact);
        log.debug("STATUS CATEGORIE : " + contact.getCategorie());

        contactService.save(contact);
        log.info("MISE A JOUR DU CONTACT");

        return "redirect:/dashboard/contact/" + contact.getId() + "/show";
    }
}
