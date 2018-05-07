package ci.imako.imakocrmtest.controllers;

import ci.imako.imakocrmtest.domain.Contact;
import ci.imako.imakocrmtest.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class ReportingController {

    private ContactService contactService;

    @Autowired
    public ReportingController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/exporter/{id}/new")
    public String generateContactPdf(Model model, @PathVariable String id) {
        Contact contact = contactService.findById(Long.valueOf(id));
        model.addAttribute("contact", contact);
        return "contactView";
    }
}
