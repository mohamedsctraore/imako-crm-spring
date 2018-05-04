package ci.imako.imakocrmtest.services.impl;

import ci.imako.imakocrmtest.domain.AccountUser;
import ci.imako.imakocrmtest.exceptions.ResourceNotFoundException;
import ci.imako.imakocrmtest.repositories.AccountUserRepository;
import ci.imako.imakocrmtest.services.AccountUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountUserServiceImpl implements AccountUserService {

    private AccountUserRepository userRepository;

    @Autowired
    public AccountUserServiceImpl(AccountUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AccountUser findByLogin(String name) {
        return userRepository.findByLogin(name)
                .orElseThrow(() -> new ResourceNotFoundException("Resource inexistante"));
    }

    @Override
    public AccountUser save(AccountUser accountUser) {
        return userRepository.save(accountUser);
    }

    @Override
    public AccountUser recoveryPassword(Long id, String password) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
