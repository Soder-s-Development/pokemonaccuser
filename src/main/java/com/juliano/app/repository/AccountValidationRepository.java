package com.juliano.app.repository;

import com.juliano.app.Models.AccountValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountValidationRepository extends JpaRepository<AccountValidation, Long> {
    AccountValidation findByCode(int code);
}
