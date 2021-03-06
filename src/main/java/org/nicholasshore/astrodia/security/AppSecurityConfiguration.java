package org.nicholasshore.astrodia.security;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppSecurityConfiguration {

    AppUserDetailsService appUserDetailsService;
    LoginFailureHandler failureHandler;

    @Autowired
    public AppSecurityConfiguration(AppUserDetailsService appUserDetailsService, LoginFailureHandler failureHandler) {
        this.appUserDetailsService = appUserDetailsService;
        this.failureHandler = failureHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() throws NullPointerException {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .and()
                .formLogin().loginPage("/user/signin")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/user/signin")
                .defaultSuccessUrl("/")
                .failureHandler(failureHandler)
                .and()
                .logout()
                .invalidateHttpSession(true).clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
        return http.build();
    }

    @Bean
    SecurityFilterChain resources(HttpSecurity http) throws Exception {
        http
                .requestMatchers((matchers) -> matchers.antMatchers("/static/**"))
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll())
                .requestCache().disable()
                .securityContext().disable()
                .sessionManagement().disable();

        return http.build();
    }

}
