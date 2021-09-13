package com.javatpoint.security;

import com.javatpoint.model.UsersAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UserSecurityDetails implements UserDetails {
    private UsersAccount usersAccount;

    public UserSecurityDetails(UsersAccount usersAccount) {
        this.usersAccount = usersAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_" + usersAccount.getRole());
        authorities.add(auth);
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.usersAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return this.usersAccount.getUser_name();
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
        return true;
    }
}