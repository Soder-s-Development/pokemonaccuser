package com.juliano.app.controller;

import com.juliano.app.Models.Personagem;
import com.juliano.app.config.Midleware;
import com.juliano.app.repository.PersonagemRepository;
import com.juliano.app.response.PersonagemResponse;
import com.juliano.app.servie.PersonagemService;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("personagem")
@AllArgsConstructor
@CrossOrigin(origins = {"*", "x-requested-with", "content-type"}, originPatterns = "*")
public class PersonagemController {
 //@CrossOrigin(origins = {"http://localhost", "http://127.0.0.1", "http://0.0.0.0", "x-requested-with", "content-type"},
	@Autowired
	private PersonagemRepository psnr;

	@Autowired
	private PersonagemService psns;

	private Midleware midleware;

	@PostMapping
	@ApiParam(value = "auth token")
	public ResponseEntity<Object> createPersonagem(@RequestBody Personagem np, @RequestHeader(value = "Authorization", required = true) String token) {
		if(!midleware.validateToken(token)) return ResponseEntity.status(401).build();
		return psns.saveNewPersonagem(np);
	}
	@GetMapping("/{id}")
	@ApiParam(value = "auth token")
	public ResponseEntity<PersonagemResponse> getPersonagem(@PathVariable Long id, @RequestHeader(value = "Authorization", required = true) String token) {
		if(!midleware.validateToken(token)) return ResponseEntity.status(401).build();
		return ResponseEntity.ok(psns.getPersonagem(id));
	}

}
