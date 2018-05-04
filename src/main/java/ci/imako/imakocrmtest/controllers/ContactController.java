package ci.imako.imakocrmtest.controllers;

import ci.imako.imakocrmtest.domain.Contact;
import ci.imako.imakocrmtest.exceptions.ResourceNotFoundException;
import ci.imako.imakocrmtest.services.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/dashboard")
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contact-list")
    public String findAll(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        log.info("Affichage de la liste des contacts");
        return "contacts/list";
    }

    @GetMapping("/contact/{id}/show")
    public String findById(Model model, @PathVariable Long id) {
        log.debug("DEBUT AFFICHAGE D'UN CONTACT");
        log.debug("ID PARAMETRE : " + id);

        Contact contact = contactService.findById(id);
        model.addAttribute("contact", contact);

        log.debug("NOM DU CONTACT : " + contact.getNom());
        log.debug("EMAIL DU CONTACT : " + contact.getEmail());
        log.debug("TELEPHONE DU CONTACT : " + contact.getTelephone());
        log.debug("CATEGORIE DU CONTACT : " + contact.getCategorie().name());

        log.info("AFFICHAGE DU CONTACT ID " + contact.getId());

        return "contacts/show";
    }

    @GetMapping("/contact/new")
    public String newContact(Model model) {
        log.debug("DEBUT AFFICHAGE FORMULAIRE CONTACT");

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

        model.addAttribute("contact", contact);

        log.info("AFFICHAGE DU FORMULAIRE MODIFICATION CONTACT");
        return "contacts/form";
    }

    @GetMapping("/contact/{id}/delete")
    public String deleteContact(@PathVariable Long id) {
        log.debug("DEBUT SUPPRESSION D'UN CONTACT");

        log.debug("ID SUPPRESSION CONTACT " + id);

        contactService.deleteById(id);

        log.info("SUPPRESSION CONTACT ID : " + id);

        return "redirect:/contacts/list";
    }

    @PostMapping("/contact")
    public String saveOrUpdate(@Valid @ModelAttribute("contact") Contact contact,
                               BindingResult result) {
        log.debug("DEBUT VALIDATION FORMULAIRE SAVE OR UPDATE");

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "contacts/form";
        }

        Contact savedContact = contactService.save(contact);

        log.debug("ID CONTACT : " + savedContact.getId());
        log.info("ENREGISTREMENT CONTACT ID : " + savedContact.getId());

        return "redirect:/dashboard/contact/" + savedContact.getId() + "/show";
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
}
