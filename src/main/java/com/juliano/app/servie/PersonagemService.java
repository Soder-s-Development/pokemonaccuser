package com.juliano.app.servie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.juliano.app.Models.PokemonUnico;

import javax.persistence.EntityNotFoundException;
import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.juliano.app.Models.Account;
import com.juliano.app.Models.Personagem;
import com.juliano.app.config.CustomResponse;
import com.juliano.app.repository.PersonagemRepository;
import com.juliano.app.repository.PokemonUnicoRepository;
import com.juliano.app.request.PersonagemRequest;
import com.juliano.app.response.PersonagemResponse;

@Service
public class PersonagemService {
    @Autowired
    PersonagemRepository pr;
    @Autowired
    AccountService accs;
    @Autowired
    PokemonUnicoRepository repository;

    public ResponseEntity<Object> saveNewPersonagem(PersonagemRequest p) {
    	ResponseEntity<Account> responseACC = accs.getAcc(p.getId_conta());
    	if(!(responseACC.getStatusCode() == HttpStatus.OK)) {
    		return ResponseEntity.status(422).body(new CustomResponse().contaNaoExistente(p));
    	}
    	if(nomeExisteNaBase(p.getNome())) {
    		return ResponseEntity.status(422).body(new CustomResponse().personagemNomeJaExiste(p));
    	}
    	
    	return ResponseEntity.ok(pr.save(Personagem.builder().nivel(1).experiencia(0).id_conta(p.getId_conta()).build()));
    }
    private boolean nomeExisteNaBase(String nome) {
    	return pr.findByNome(nome).size() > 0;
    }
    
   public PersonagemResponse getPersonagem(Long id) {
	   Optional<Personagem> optionalPersonagem = pr.findById(id);
	   if(!optionalPersonagem.isPresent()) {
		   return null;
	   }
	   Personagem personagem = optionalPersonagem.get();
	   List<PokemonUnico> pokemons = new ArrayList<>();
	   personagem.getHolds().forEach(
			   	idPokemon -> { 
			   		try {
			   			Optional<PokemonUnico> p = repository.findById(idPokemon);
						if(p.isPresent()) {pokemons.add(p.get());}
					} catch (EntityNotFoundException e) {
						e.printStackTrace();
					} 
			   	});
	   
	   return PersonagemResponse.builder()
			   .id(personagem.getId())
			   .id_conta(personagem.getId_conta())
			   .nivel(personagem.getNivel())
			   .nome(personagem.getNome())
			   .holds(Set.copyOf(pokemons))
			   .build();
   }
}
