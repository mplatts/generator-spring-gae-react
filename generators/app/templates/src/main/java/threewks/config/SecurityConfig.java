package threewks.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.contrib.gae.security.UserAdapter;
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
import threewks.model.model.User;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

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
                .ignoringAntMatchers("/system/**", "/_ah/**")
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
                .antMatchers("/_ah/**").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers(GET, "/api/reference-data").permitAll()
                .antMatchers("/system/**", "/task/**").permitAll()  // protected by security-constraint in web.xml which delegates to GCP's IAM
                .antMatchers("/api/**").authenticated()
                .antMatchers("/**").permitAll();
        // @formatter:on
    }

    //TODO: Re-enable
//    @Bean
//    public Class<User> gaeUserClass() {
//        return User.class;
//    }
//
//    @Bean
//    public UserAdapter<User> gaeUserHelper(UserService userService) {
//        return UserAdapterImpl.byEmail(userService);
//    }
}
