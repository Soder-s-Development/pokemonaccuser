package com.juliano.app.controller;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import com.juliano.app.config.Midleware;
import io.swagger.annotations.ApiImplicitParam;
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

	@Autowired
	private Midleware midleware;
	
	@PostMapping
	public Account createAccount(@Valid @RequestBody Account acc) {return accs.newAcc(acc);}
	@GetMapping("/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<Account> getAccount(@PathVariable Long id, ServletRequest servletRequest) {
		return midleware.tokenRequest(servletRequest) ? accs.getAcc(id) : ResponseEntity.status(401).build();
	}
	@PostMapping("/{id}/{nome}")
	public ResponseEntity<Personagem> criarPersonagem(@PathVariable String nome, @PathVariable Long id, ServletRequest servletRequest){
		return midleware.tokenRequest(servletRequest) ? ResponseEntity.ok(accs.criarPersonagem(id, nome)) : ResponseEntity.status(401).build();
	}
	@PostMapping("/validate/{code}")
	public Boolean activeAcc(@PathVariable int code, ServletRequest servletRequest){
		return midleware.tokenRequest(servletRequest) ? accs.validarEmail(code) : false;
	}

	@PatchMapping("/levelUP/{id}")
	public int subirContaDeNivel(@PathVariable Long id, ServletRequest servletRequest){
		return midleware.tokenRequest(servletRequest) ? accs.subirDeNivel(id) : -1;
	}
	@PatchMapping("/experience/{id}/{quantidade}")
	public ResponseEntity<Integer> adicionarExperiencia(@PathVariable Long id, @PathVariable int quantidade, ServletRequest servletRequest){
		return midleware.tokenRequest(servletRequest) ? accs.salvarExperiencia(id, quantidade) : ResponseEntity.status(401).build();
	}
}
