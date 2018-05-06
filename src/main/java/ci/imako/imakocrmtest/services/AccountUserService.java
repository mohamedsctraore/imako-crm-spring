package ci.imako.imakocrmtest.services;

import ci.imako.imakocrmtest.domain.AccountUser;

public interface AccountUserService {
    AccountUser findByLogin(String name);
    AccountUser save(AccountUser accountUser);
    AccountUser recoveryPassword(Long id, String password);
    AccountUser currentUser();
    void delete(Long id);
}
