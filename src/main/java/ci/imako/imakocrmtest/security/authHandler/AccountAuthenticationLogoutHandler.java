package ci.imako.imakocrmtest.security.authHandler;

import ci.imako.imakocrmtest.domain.AccountUser;
import ci.imako.imakocrmtest.model.AccountDetails;
import ci.imako.imakocrmtest.repositories.AccountUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccountAuthenticationLogoutHandler implements LogoutSuccessHandler {

    private AccountUserRepository accountUserRepository;

    @Autowired
    public AccountAuthenticationLogoutHandler(AccountUserRepository accountUserRepository) {
        this.accountUserRepository = accountUserRepository;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)
            throws IOException, ServletException {

        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
        AccountUser accountUser = accountDetails.getAccountUser();
        accountUser.setStateConnection(false);
        accountUserRepository.save(accountUser);
        response.sendRedirect("/login");
    }
}
