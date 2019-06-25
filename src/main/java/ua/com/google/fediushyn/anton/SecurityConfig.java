package ua.com.google.fediushyn.anton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ua.com.google.fediushyn.anton.service.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final CustomSuccessHandler customSuccessHandler;

    @Autowired
    SecurityConfig(UserDetailsServiceImpl userDetailsService,
                   CustomSuccessHandler customSuccessHandler) {
        super();
        this.userDetailsService = userDetailsService;
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                .antMatchers("/")
                    .hasAnyRole("USER", "ADMIN", "ANONYMOUS")
                .antMatchers("/admin")
                    .hasRole("ADMIN")
                .antMatchers("/addFilm")
                    .hasRole("ADMIN")
                .antMatchers("/register")
                    .permitAll()
                .and()
        .exceptionHandling()
                .accessDeniedPage("/unauthorized")
                .and()
        .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/j_spring_security_check")
                .successHandler(customSuccessHandler)
                .failureUrl("/?loginerror")
                .usernameParameter("j_login")
                .passwordParameter("j_password")
                .permitAll()
                .and()
        .logout()
                .permitAll()
                .logoutUrl("/unauthorized")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
    }
}
