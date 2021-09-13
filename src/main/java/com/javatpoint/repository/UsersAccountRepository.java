package com.javatpoint.repository;

import com.javatpoint.model.UsersAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UsersAccountRepository extends JpaRepository<UsersAccount, Integer> {

    @Transactional
    @Query(value = "SELECT * FROM users_account WHERE user_name = :userName", nativeQuery = true)
    Optional<UsersAccount> findByUsername(String userName);
}
