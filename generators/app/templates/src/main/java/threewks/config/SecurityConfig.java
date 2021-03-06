package threewks.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.contrib.gae.security.config.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import threewks.framework.usermanagement.model.User;
import threewks.framework.usermanagement.model.UserAdapterGae;
import threewks.framework.usermanagement.service.UserService;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider daoAuthenticationProvider;
    @Autowired
    private RememberMeServices rememberMeServices;
    @Autowired
    private AuthenticationSuccessHandler restAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler restAuthenticationFailureHandler;
    @Autowired
    private LogoutSuccessHandler restLogoutSuccessHandler;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf()
                .ignoringAntMatchers("/system/**", "/task/**", "/cron/**", "/_ah/**")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
                .exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), new AntPathRequestMatcher("/api/**"))
            .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices)
                .key(securityProperties.getRememberMe().getKey())
            .and()
                .formLogin()
                .loginPage("/login") // Stop spring from generating its own login page
                .loginProcessingUrl("/api/login")
                .successHandler(restAuthenticationSuccessHandler)
                .failureHandler(restAuthenticationFailureHandler)
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(restLogoutSuccessHandler)
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers("/_ah/**", "/system/**", "/task/**", "/cron/**").permitAll()  // protected by security-constraint in web.xml which delegates to GCP's IAM
                .antMatchers("/api/login", "/api/users/me", "/api/error/**").permitAll()
                .antMatchers(GET, "/api/reference-data").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/**").permitAll()
            .and()
                .headers()
                .contentSecurityPolicy("default-src 'self'; script-src 'self' https://cdn.polyfill.io; style-src 'self' 'unsafe-inline' https://fonts.googleapis.com blob:; font-src 'self' https://fonts.gstatic.com");
        // @formatter:on
    }

    @Bean
    public Class<User> gaeUserClass() {
        return User.class;
    }

    @Bean
    public UserAdapterGae gaeUserHelper(UserService userService) {
        return UserAdapterGae.byEmail(userService);
    }
}
