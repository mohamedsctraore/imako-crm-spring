package ci.imako.imakocrmtest.model;

import ci.imako.imakocrmtest.domain.AccountUser;
import ci.imako.imakocrmtest.domain.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AccountDetails implements UserDetails {

    private AccountUser accountUser;

    public AccountDetails(AccountUser accountUser) {
        this.accountUser = accountUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> authorities = accountUser.getAuthorities();
        if (!authorities.isEmpty()) {
            final String[] allNameAuthorities = {""};
            authorities.forEach(authority
                    -> {
                if (!allNameAuthorities[0].isEmpty()) {
                    allNameAuthorities[0] = "," + authority.getName();
                } else {
                    allNameAuthorities[0] = authority.getName();
                }
            });
            return AuthorityUtils.commaSeparatedStringToAuthorityList(allNameAuthorities[0]);
        }
        return AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public String getPassword() {
        return accountUser.getPassword();
    }

    @Override
    public String getUsername() {
        return accountUser.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return accountUser.isEnabled();
    }

    public AccountUser getAccountUser() {
        return accountUser;
    }
}
