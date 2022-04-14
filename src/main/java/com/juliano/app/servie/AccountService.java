package com.juliano.app.servie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juliano.app.Models.Account;
import com.juliano.app.repository.AccountsRepository;
import com.juliano.app.security.PasswordUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {

	@Autowired
	private AccountsRepository accr;
	
	@Autowired
	private PasswordUtils passu;
	
	
	public Account newAcc(Account acc) {
		acc.setPassword(passu.generateSecurePassword(acc.getPassword(), "mypokemongame"));
		return accr.save(acc);
	}
	
	public Account getAcc(Long id) {
		return accr.getById(id);
	}
}
