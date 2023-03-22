package com.juliano.app.controller;

import static com.juliano.app.config.CustomResponse.naoEncontrado;

import javax.naming.AuthenticationException;
import com.juliano.app.config.Midleware;
import com.juliano.app.repository.PersonagemRepository;
import com.juliano.app.request.PersonagemRequest;
import com.juliano.app.response.PersonagemResponse;
import com.juliano.app.servie.PersonagemService;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
	public ResponseEntity<Object> createPersonagem(@RequestBody PersonagemRequest np, @RequestHeader(value = "Authorization", required = true) String token) throws AuthenticationException, RuntimeException {
		if(!midleware.validateToken(token)) return midleware.tokenOk(token); 
		return psns.saveNewPersonagem(np);
	}
	@GetMapping("/{id}")
	@ApiParam(value = "auth token")
	public ResponseEntity<Object> getPersonagem(@PathVariable Long id, @RequestHeader(value = "Authorization", required = true) String token) throws NotFoundException {
		if(!midleware.validateToken(token)) return ResponseEntity.status(401).build();
		PersonagemResponse p = psns.getPersonagem(id);
		return p != null ? ResponseEntity.ok(p) : ResponseEntity.status(404).body(naoEncontrado("Personagem não encontrado", p));
	}

}
