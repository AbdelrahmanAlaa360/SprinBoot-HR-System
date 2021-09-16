package com.javatpoint.repository;

import com.javatpoint.model.UsersAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UsersAccount, Integer> {

}
