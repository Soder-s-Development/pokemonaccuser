package com.juliano.app.servie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.juliano.app.Models.Account;
import com.juliano.app.Models.Personagem;
import com.juliano.app.repository.AccountsRepository;
import com.juliano.app.repository.PersonagemRepository;
import com.juliano.app.security.PasswordUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {

	@Autowired
	private AccountsRepository accr;
	
	@Autowired
	private PasswordUtils passu;
	
	@Autowired
	private PersonagemRepository pr;
	
	
	public Account newAcc(Account acc) {
		acc.setPassword(passu.generateSecurePassword(acc.getPassword(), "mypokemongame"));
		return accr.save(acc);
	}
	
	public ResponseEntity<Account> getAcc(Long id) {
		return accr.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	public Personagem criarPersonagem(Long id, String nome){
		Personagem p = new Personagem();
		p.setNome(nome);
		p.setId_conta(id);
		return pr.save(p);
	}
}
