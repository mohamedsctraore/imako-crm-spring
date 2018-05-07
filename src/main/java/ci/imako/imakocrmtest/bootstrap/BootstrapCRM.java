package ci.imako.imakocrmtest.bootstrap;

import ci.imako.imakocrmtest.domain.AccountUser;
import ci.imako.imakocrmtest.domain.Authority;
import ci.imako.imakocrmtest.services.AccountUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BootstrapCRM implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(BootstrapCRM.class);

    private AccountUserService accountUserService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapCRM(PasswordEncoder passwordEncoder,
                        AccountUserService accountUserService) {
        this.accountUserService = accountUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("DEBUT DU CHARGEMENT DES DONNEES");
        if (accountUserService.findByLogin("mohamed") == null) {
            accountUserService.save(getUserAccount());
        }
        log.info("CHARGEMENT DES DONNEES DE L'APPLICATION TERMINE");
    }

    private AccountUser getUserAccount() {
        AccountUser accountUser = new AccountUser();
        accountUser.setLogin("mohamed");
        accountUser.setEmail("mohamed.imako@imako.digital");
        accountUser.setEnabled(true);
        accountUser.setFirstName("Mohamed");
        accountUser.setPassword(passwordEncoder.encode("mangeonsbien"));

        Authority authority = new Authority();
        authority.setName("commercial");

        accountUser.addAuthority(authority);

        return accountUser;
    }
}
