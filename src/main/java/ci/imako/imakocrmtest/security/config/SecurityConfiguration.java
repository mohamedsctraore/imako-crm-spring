package ci.imako.imakocrmtest.security.config;

import ci.imako.imakocrmtest.security.AccountDetailsService;
import ci.imako.imakocrmtest.security.authHandler.AccountAuthenticationLogoutHandler;
import ci.imako.imakocrmtest.security.authHandler.AccountAuthenticationSuccessfulHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountDetailsService accountDetailsService;
    @Autowired
    private AccountAuthenticationSuccessfulHandler successfulHandler;
    @Autowired
    private AccountAuthenticationLogoutHandler logoutHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/", "/resources/**", "/static/**", "/public/**", "/webui/**", "/console/**",
                "/configuration/**", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg",
                "/**/*.gif", "/**/*.svg", "/**/*.ico", "/**/*.ttf", "/**/*.woff", "/**/*.min.css");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .formLogin()
                    .permitAll()
                    .loginPage("/login")
                    .successHandler(successfulHandler)
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .permitAll()
                    .logoutSuccessHandler(logoutHandler)
                .and()
                    .authorizeRequests()
                    .antMatchers("/")
                .permitAll()
                .antMatchers("/api/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(accountDetailsService);
        return authenticationProvider;
    }
}
