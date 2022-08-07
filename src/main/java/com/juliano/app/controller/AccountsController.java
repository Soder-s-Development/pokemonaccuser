package com.juliano.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@PatchMapping("/levelUP/{id}")
	public int subirContaDeNivel(@PathVariable Long id){
		return  accs.subirDeNivel(id);
	}
	@PatchMapping("/experience/{id}/{quantidade}")
	public ResponseEntity<Integer> adicionarExperiencia(@PathVariable Long id, @PathVariable int quantidade){
		return accs.salvarExperiencia(id, quantidade);
	}

}
