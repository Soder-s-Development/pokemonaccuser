package com.juliano.app.controller;

import com.juliano.app.servie.PersonagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.juliano.app.Models.Personagem;
import com.juliano.app.repository.PersonagemRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("personagem")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost", "http://127.0.0.1", "http://0.0.0.0", "x-requested-with", "content-type"}, originPatterns = "*")
public class PersonagemController {

	@Autowired
	private PersonagemRepository psnr;

	private PersonagemService psns;
	
	@PostMapping
	public Personagem createPersonagem(@RequestBody Personagem np) {
		return psnr.save(np);
	}
	@GetMapping("/{id}")
	public Personagem getPersonagem(@PathVariable Long id) {
		return psnr.findById(id).orElse(null);
	}

	@PatchMapping("/experience/{id}/{quantidade}")
	public ResponseEntity<Personagem> setExperience(@PathVariable Long id, @PathVariable int quantidade){
		return psns.setNewExperience(id, quantidade);
	}
}
