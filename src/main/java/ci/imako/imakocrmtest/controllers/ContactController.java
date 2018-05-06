package ci.imako.imakocrmtest.controllers;

import ci.imako.imakocrmtest.domain.Commande;
import ci.imako.imakocrmtest.domain.Contact;
import ci.imako.imakocrmtest.domain.RendezVous;
import ci.imako.imakocrmtest.exceptions.ResourceNotFoundException;
import ci.imako.imakocrmtest.exceptions.ServerErrorException;
import ci.imako.imakocrmtest.services.CommandeService;
import ci.imako.imakocrmtest.services.ContactService;
import ci.imako.imakocrmtest.services.RendezVousService;
import ci.imako.imakocrmtest.validators.ContactValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private ContactService contactService;
    private RendezVousService rendezVousService;
    private CommandeService commandeService;
    private ContactValidator contactValidator;

    @Autowired
    public ContactController(ContactService contactService,
                             RendezVousService rendezVousService,
                             CommandeService commandeService, ContactValidator contactValidator) {
        this.contactService = contactService;
        this.rendezVousService = rendezVousService;
        this.commandeService = commandeService;
        this.contactValidator = contactValidator;
    }

    @GetMapping("/contact-list")
    public String findAll(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        log.info("Affichage de la liste des contacts");
        return "contacts/list";
    }

    @GetMapping("/contact/{id}/show")
    public String findById(Model model, @PathVariable String id) {
        log.debug("DEBUT AFFICHAGE D'UN CONTACT");
        log.debug("ID PARAMETRE : " + id);

        Contact contact = contactService.findById(Long.valueOf(id));
        log.debug("NOM DU CONTACT : " + contact.getNom());
        log.debug("EMAIL DU CONTACT : " + contact.getEmail());
        log.debug("TELEPHONE DU CONTACT : " + contact.getTelephone());
        log.debug("CATEGORIE DU CONTACT : " + contact.getCategorie().name());

        model.addAttribute("contact", contact);

        List<RendezVous> rendezVousList = rendezVousService.findByRendezVousById(Long.valueOf(id));
        log.debug("NOMBRE DE RENDEZ VOUS LIE A CE CONTACT : " + rendezVousList.size());
        for (RendezVous rendezVous : rendezVousList) {
            log.debug(rendezVous.getId().toString());
            log.debug(rendezVous.getDateRendezVous().toString());
            log.debug(rendezVous.getNoteRendezVous());
            log.debug("");
        }

        model.addAttribute("rendezVousList", rendezVousList);

        List<Commande> commandeList = commandeService.findCommandesById(Long.valueOf(id));
        log.debug("NOMBRE DE COMMANDES LIE A CE CONTACT : " + commandeList.size());
        for (Commande commande : commandeList) {
            log.debug(commande.getId().toString());
            log.debug(commande.getNoteCommande());
            log.debug("");
        }

        model.addAttribute("commandeList", commandeList);

        log.info("AFFICHAGE DU CONTACT ID " + contact.getId());

        return "contacts/show";
    }

    @GetMapping("/contact/new")
    public String newContact(Model model) {
        log.info("DEBUT AFFICHAGE FORMULAIRE CONTACT");

        model.addAttribute("contact", new Contact());

        log.info("AFFICHAGE DU FORMULAIRE DE CONTACT");

        return "contacts/form";
    }

    @GetMapping("/contact/{id}/update")
    public String updateContact(Model model, @PathVariable Long id) {
        log.debug("DEBUT AFFICHAGE MODIFICATION CONTACT");
        log.debug("ID CONTACT : " + id);

        Contact contact = contactService.findById(id);

        log.debug("ID CONTACT MODIFIE " + contact.getId());
        log.debug("CATEGORIE CONTACT A MODIFIER : " + contact.getCategorie());

        model.addAttribute("contact", contact);

        log.info("AFFICHAGE DU FORMULAIRE MODIFICATION CONTACT");
        return "contacts/form";
    }

    @GetMapping("/contact/{id}/delete")
    public String deleteContact(@PathVariable Long id) {
        log.info("DEBUT SUPPRESSION D'UN CONTACT");

        log.debug("ID SUPPRESSION CONTACT " + id);

        contactService.deleteById(id);

        log.info("SUPPRESSION CONTACT ID : " + id);

        return "redirect:/dashboard/contact-list";
    }

    @PostMapping("/contact")
    public String saveOrUpdate(@Valid @ModelAttribute("contact") Contact contact,
                               BindingResult result, RedirectAttributes redirectAttributes) {
        log.info("DEBUT VALIDATION FORMULAIRE SAVE OR UPDATE");

        contactValidator.validate(contact, result);
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            redirectAttributes.addFlashAttribute("errorFlash", "Un ou plusieurs erreurs " +
                    "dans le formulaire");

            return "contacts/form";
        }

        Contact savedContact = contactService.save(contact);

        log.debug("ID CONTACT : " + savedContact.getId());
        log.info("ENREGISTREMENT CONTACT ID : " + savedContact.getId());

        redirectAttributes.addFlashAttribute("successFlash", "Enregistrement effectué avec succès");

        return "redirect:/dashboard/contact/" + savedContact.getId() + "/show";
    }

    @PostMapping("/contact/search")
    public String searchContacts(@RequestParam("mot_cle") String motCle, Model model) {
        log.info("DEBUT DE LA RECHERCHE LIKE LE NOM");

        model.addAttribute("contacts", contactService.findAllNomLike(motCle));
        return "contacts/list";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/404");
        modelAndView.addObject("exception",exception);

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServerErrorException.class)
    public ModelAndView handleErrorServer(Exception exception) {
        log.error("Internal server error");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/500");
        modelAndView.addObject("exception",exception);

        return modelAndView;
    }
}
