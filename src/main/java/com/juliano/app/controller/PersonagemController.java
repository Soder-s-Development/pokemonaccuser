package com.juliano.app.controller;

import static com.juliano.app.config.CustomResponse.naoEncontrado;

import javax.naming.AuthenticationException;
import com.juliano.app.config.Midleware;
import com.juliano.app.config.RespostaPadrao;
import com.juliano.app.exceptions.CustomNotAllowedException;
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

import java.util.Objects;

@RestController
@RequestMapping("personagem")
@AllArgsConstructor
public class PersonagemController {

	@Autowired
	private PersonagemService personagemService;

	@PostMapping
	@ApiParam(value = "auth token")
	public ResponseEntity<Object> createPersonagem(@RequestBody PersonagemRequest np, @RequestHeader(value = "Authorization", required = true) String token) throws AuthenticationException, RuntimeException {
		return personagemService.saveNewPersonagem(np);
	}
	@GetMapping("/{id}")
	@ApiParam(value = "auth token")
	public ResponseEntity<Object> getPersonagem(@PathVariable Long id, @RequestHeader(value = "Authorization", required = true) String token) throws NotFoundException {
		PersonagemResponse p = personagemService.getPersonagem(id);
		return p != null ? ResponseEntity.ok(p) : ResponseEntity.status(404).body(naoEncontrado("Personagem não encontrado", p));
	}

	@GetMapping("/all")
	@ApiParam(value = "auth token")
	public ResponseEntity<RespostaPadrao> buscarTodosPersonagens(@RequestHeader(value = "Authorization", required = true) String token) throws CustomNotAllowedException {
		var t = Midleware.getTokenEValidate(token);
		if (t == null || t.getEmail().isEmpty()) {
			throw new CustomNotAllowedException("Necessário um e-mail válido");
		}
		return personagemService.buscarTodosPersonagens(t.getEmail());
	}

}
