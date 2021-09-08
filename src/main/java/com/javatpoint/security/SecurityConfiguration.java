package com.javatpoint.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("manager").password(passwordEncoder().encode("manager123")).roles("MANAGER")
            .and()
            .withUser("user").password(passwordEncoder().encode("user123")).roles("USER")
            .and()
            .withUser("hr").password(passwordEncoder().encode("hr123")).roles("HR");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers().permitAll()
                .antMatchers("/HR/salary-history/add").hasAnyRole("HR","MANAGER")
                .antMatchers("/HR/salary-history/get-salary").hasAnyRole("HR","MANAGER","USER")
                .antMatchers("/HR/vacations").hasRole("HR")
                .antMatchers("/HR").hasRole("HR")
                .antMatchers("/HR/add-department").hasAnyRole("MANAGER", "HR")
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
