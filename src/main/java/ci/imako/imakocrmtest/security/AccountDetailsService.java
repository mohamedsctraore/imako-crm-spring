package ci.imako.imakocrmtest.security;

import ci.imako.imakocrmtest.domain.AccountUser;
import ci.imako.imakocrmtest.model.AccountDetails;
import ci.imako.imakocrmtest.services.AccountUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {

    private AccountUserService userService;

    @Autowired
    public AccountDetailsService(AccountUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountUser accountUser = userService.findByLogin(username);
        if (accountUser != null) {
            return new AccountDetails(accountUser);
        }
        throw new UsernameNotFoundException("This Account does not exist");
    }
}
