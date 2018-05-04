package ci.imako.imakocrmtest.security.authHandler;

import ci.imako.imakocrmtest.domain.AccountUser;
import ci.imako.imakocrmtest.domain.Sessions;
import ci.imako.imakocrmtest.model.AccountDetails;

import ci.imako.imakocrmtest.repositories.AccountUserRepository;
import ci.imako.imakocrmtest.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class AccountAuthenticationSuccessfulHandler implements AuthenticationSuccessHandler {

    private AccountUserRepository accountUserRepository;
    private SessionRepository sessionsRepository;

    @Autowired
    public AccountAuthenticationSuccessfulHandler(AccountUserRepository accountUserRepository,
                                                  SessionRepository sessionsRepository) {
        this.accountUserRepository = accountUserRepository;
        this.sessionsRepository = sessionsRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Sessions sessions = new Sessions();
        HttpSession session = request.getSession(true);
        String idSession = session.getId();
        long creationTime = session.getCreationTime();
        String ipClient = request.getRemoteAddr();
        String agentSession = request.getHeader("User-Agent");
        Instant instant = Instant.ofEpochMilli(creationTime);
        LocalDateTime loginTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        if (idSession != null) {
            sessions.setIdSessionCurrentSession(idSession);
        }
        if (ipClient != null) {
            sessions.setIpAddressSession(ipClient);
        }
        if (agentSession != null) {
            sessions.setAgentSession(agentSession);
        }
        sessions.setLoginTime(loginTime);
        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
        AccountUser accountUser = accountDetails.getAccountUser();
        if (accountUser != null) {
            sessionsRepository.save(sessions);
            accountUser.addSession(sessions);
            accountUser.setStateConnection(true);
            accountUserRepository.save(accountUser);
            session.setMaxInactiveInterval(3600);
            session.setAttribute("account", accountUser);
        }
        response.sendRedirect("/dashboard");
    }
}
