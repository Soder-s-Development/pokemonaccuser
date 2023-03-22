package com.juliano.app.controller;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import com.juliano.app.accdtos.LoginDTO;
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
		return midleware.getTokenEValidate(servletRequest) ? accs.getAcc(id) : ResponseEntity.status(401).build();
	}
	@PostMapping("/{id}/{nome}")
	public ResponseEntity<Personagem> criarPersonagem(@PathVariable String nome, @PathVariable Long id, ServletRequest servletRequest){
		return midleware.getTokenEValidate(servletRequest) ? ResponseEntity.ok(accs.criarPersonagem(id, nome)) : ResponseEntity.status(401).build();
	}
	@PostMapping("/validate/{code}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<Boolean> activeAcc(@PathVariable int code, ServletRequest servletRequest){

		return midleware.getTokenEValidate(servletRequest) ? accs.validarEmail(code) : ResponseEntity.badRequest()
				.body(false);
	}

	@PatchMapping("/levelUP/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public int subirContaDeNivel(@PathVariable Long id, ServletRequest servletRequest){
		return midleware.getTokenEValidate(servletRequest) ? accs.subirDeNivel(id) : -1;
	}
	@PatchMapping("/experience/{id}/{quantidade}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<Integer> adicionarExperiencia(@PathVariable Long id, @PathVariable int quantidade, ServletRequest servletRequest){
		return midleware.getTokenEValidate(servletRequest) ? accs.salvarExperiencia(id, quantidade) : ResponseEntity.status(401).build();
	}

	PostMapping("/login")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public  ResponseEntity doLogin(@RequestBody @Valid LoginDTO login){
		return accs.login(login);
	}

}
