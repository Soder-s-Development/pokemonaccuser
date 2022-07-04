package com.juliano.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@PostMapping
	public Personagem createPersonagem(@RequestBody Personagem np) {
		return psnr.save(np);
	}
	@GetMapping("/{id}")
	public Personagem getPersonagem(@PathVariable Long id) {
		return psnr.findById(id).orElse(null);
	}
}
