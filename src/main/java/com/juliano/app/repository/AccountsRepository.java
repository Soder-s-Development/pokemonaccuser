package com.juliano.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juliano.app.Models.Account;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long>{
    Account findByEmail(String s);

    @Query("SELECT a.id FROM Account a WHERE a.email = :email")
    Long findAccountIdByEmail(@Param("email") String email);
}
