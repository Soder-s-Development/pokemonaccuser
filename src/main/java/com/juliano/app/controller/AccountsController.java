package com.juliano.app.controller;

import java.net.http.HttpResponse;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juliano.app.Models.Account;
import com.juliano.app.servie.AccountService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/account")
@CrossOrigin(origins = {"http://localhost", "http://127.0.0.1", "http://0.0.0.0", "x-requested-with", "content-type"}, originPatterns = "*")
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
}
