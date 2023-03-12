package com.juliano.app.controller;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import com.juliano.app.config.Midleware;
import com.juliano.app.config.RespostaPadrao;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.juliano.app.Models.Account;
import com.juliano.app.Models.Personagem;
import com.juliano.app.builder.PageableBuilder;
import com.juliano.app.servie.AccountService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/account")
@CrossOrigin(origins = { "*", "x-requested-with", "content-type" }, originPatterns = "*")
public class AccountsController {

	@Autowired
	private AccountService accs;

	@Autowired
	private Midleware midleware;

	@PostMapping
	public RespostaPadrao createAccount(@Valid @RequestBody Account acc) {
		return accs.newAcc(acc);
	}

	@GetMapping("/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<Account> getAccount(@PathVariable Long id, ServletRequest servletRequest) {
		return midleware.tokenRequest(servletRequest) ? accs.getAcc(id) : ResponseEntity.status(401).build();
	}
	
	@GetMapping("/all")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<RespostaPadrao> findAllAccount(@RequestBody PageableBuilder pageable, ServletRequest servletRequest) {
		return midleware.tokenRequest(servletRequest) ? 
				ResponseEntity.ok(accs.findAllPageable(pageable.buildPage())) : ResponseEntity.status(401).build();
	}

	@PostMapping("/{id}/{nome}")
	public ResponseEntity<Personagem> criarPersonagem(@PathVariable String nome, @PathVariable Long id,
			ServletRequest servletRequest) {
		return midleware.tokenRequest(servletRequest) ? ResponseEntity.ok(accs.criarPersonagem(id, nome))
				: ResponseEntity.status(401).build();
	}

	@PostMapping("/validate/{code}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<Boolean> activeAcc(@PathVariable int code, ServletRequest servletRequest) {

		return midleware.tokenRequest(servletRequest) ? accs.validarEmail(code)
				: ResponseEntity.badRequest().body(false);
	}

	@PatchMapping("/levelUP/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public int subirContaDeNivel(@PathVariable Long id, ServletRequest servletRequest) {
		return midleware.tokenRequest(servletRequest) ? accs.subirDeNivel(id) : -1;
	}

	@PatchMapping("/experience/{id}/{quantidade}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<Integer> adicionarExperiencia(@PathVariable Long id, @PathVariable int quantidade,
			ServletRequest servletRequest) {
		return midleware.tokenRequest(servletRequest) ? accs.salvarExperiencia(id, quantidade)
				: ResponseEntity.status(401).build();
	}
}
