package com.javatpoint.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserSecurityDetailsService userSecurityDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").
                password(passwordEncoder().encode("admin123")).roles("ADMIN")
                .and()
                .withUser("user").password(passwordEncoder().encode("user123")).roles("EMPLOYEE")
                .and()
                .withUser("hr").password(passwordEncoder().encode("hr123")).roles("HR");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/HR/**").hasAnyRole("ADMIN", "HR")
                .antMatchers("/HR/get-salary/").authenticated()
                .antMatchers("/HR/salary-history/**").hasAnyRole("ADMIN","HR")
                .antMatchers("/HR/salary-history/get-salary").hasRole("EMPLOYEE")
                .antMatchers("/HR/vacations/**").hasAnyRole("ADMIN","HR", "EMPLOYEE")
                .antMatchers("/HR/vacations/get-vacation").authenticated()
                .antMatchers("/").permitAll()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userSecurityDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

