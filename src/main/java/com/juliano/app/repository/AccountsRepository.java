package com.juliano.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juliano.app.Models.Account;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long>{
    Account findByEmail(String s);
}
