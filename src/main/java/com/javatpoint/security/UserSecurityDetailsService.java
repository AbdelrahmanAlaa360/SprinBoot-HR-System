package com.javatpoint.security;

import com.javatpoint.model.UsersAccount;
import com.javatpoint.repository.UsersAccountRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSecurityDetailsService implements UserDetailsService {
    @Autowired
    private UsersAccountRepository usersAccountRepository;
    @Autowired
    private Optional<UsersAccount> usersAccount;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String s) {
        usersAccount = usersAccountRepository.findByUsername(s);
        if(usersAccount.isPresent()){
            return null;
        }
        UserSecurityDetails userSecurityDetails = new UserSecurityDetails(usersAccount.get());
        return userSecurityDetails;
    }
}
