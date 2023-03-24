package com.juliano.app.controller;

import com.juliano.app.Models.Account;
import com.juliano.app.Models.Personagem;
import com.juliano.app.accdtos.LoginDTO;
import com.juliano.app.builder.PageableBuilder;
import com.juliano.app.config.Midleware;
import com.juliano.app.config.RespostaPadrao;
import com.juliano.app.servie.AccountService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/account")
@CrossOrigin(origins = { "*", "x-requested-with", "content-type" }, originPatterns = "*")
public class AccountsController {

	@Autowired
	private AccountService accs;

	@Autowired
	private Midleware midleware;

	@PostMapping("/create")
	public RespostaPadrao createAccount(@Valid @RequestBody Account acc) {
		return accs.newAcc(acc);
	}

	@GetMapping("/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<Account> getAccount(@PathVariable Long id, ServletRequest servletRequest) {
		return accs.getAcc(id);
	}

	@GetMapping("/all")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<RespostaPadrao> findAllAccount(@RequestBody PageableBuilder pageable, ServletRequest servletRequest) {
		return ResponseEntity.ok(accs.findAllPageable(pageable.buildPage()));
	}

	@PostMapping("/{id}/{nome}")
	public ResponseEntity<Personagem> criarPersonagem(@PathVariable String nome, @PathVariable Long id, ServletRequest servletRequest){
		return ResponseEntity.ok(accs.criarPersonagem(id, nome));
	}

	@PostMapping("/validate/{code}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<Boolean> activeAcc(@PathVariable int code, ServletRequest servletRequest){
		return accs.validarEmail(code);
	}

	@PatchMapping("/levelUP/{id}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public int subirContaDeNivel(@PathVariable Long id, ServletRequest servletRequest){
		return accs.subirDeNivel(id);
	}

	@PatchMapping("/experience/{id}/{quantidade}")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<Integer> adicionarExperiencia(@PathVariable Long id, @PathVariable int quantidade, ServletRequest servletRequest){
		return accs.salvarExperiencia(id, quantidade);
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity doLogin(@RequestBody @Valid LoginDTO login){
		return accs.login(login);
	}

}
