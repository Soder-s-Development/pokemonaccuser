package com.juliano.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juliano.app.Models.Account;
import com.juliano.app.Models.Personagem;
import com.juliano.app.servie.AccountService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/account")
@CrossOrigin(origins = {"*", "x-requested-with", "content-type"}, originPatterns = "*")
public class AccountsController {

	@Autowired
	private AccountService accs;
	
	@PostMapping
	public Account createAccount(@Valid @RequestBody Account acc) {
		return accs.newAcc(acc);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable Long id) {
		return accs.getAcc(id);
	}
	@PostMapping("/{id}/{nome}")
	public Personagem criarPersonagem(@PathVariable String nome, @PathVariable Long id){
		return accs.criarPersonagem(id, nome);
	}
	@PostMapping("/validate/{code}")
	public Boolean activeAcc(@PathVariable int code){
		return  accs.validarEmail(code);
	}
	
}
