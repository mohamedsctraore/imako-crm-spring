package ci.imako.imakocrmtest.validators;

import ci.imako.imakocrmtest.domain.Contact;
import ci.imako.imakocrmtest.repositories.ContactRepository;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class ContactValidator implements Validator {

    private ContactRepository contactRepository;

    @Autowired
    public ContactValidator(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Contact.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(@Nullable Object o, Errors errors) {
        Contact contact = (Contact) o;
        String email = contact.getEmail();
        String telephone = contact.getTelephone();

        Optional<Contact> emailContact = contactRepository.findByEmail(email);
        Optional<Contact> telephoneContact = contactRepository.findByTelephone(telephone);

        if (emailContact.isPresent()) {
            errors.rejectValue("email",
                    "email.exists",
                    new Object[] {email},
                    "Email " + email + " déj) utilisé");
        }

        if (telephoneContact.isPresent()) {
            errors.rejectValue("telephone",
                    "telephone.exists",
                    new Object[] {telephone},
                    "Telephone " + telephone + " déjà utilisé.");
        }

    }
}
