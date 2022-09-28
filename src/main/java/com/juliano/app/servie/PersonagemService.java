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
import com.juliano.app.config.CustomErrorHandler;
import com.juliano.app.repository.PersonagemRepository;
import com.juliano.app.repository.PokemonUnicoRepository;
import com.juliano.app.response.PersonagemResponse;

@Service
public class PersonagemService {
    @Autowired
    PersonagemRepository pr;
    @Autowired
    AccountService accs;
    @Autowired
    PokemonUnicoRepository repository;

    public ResponseEntity<Object> saveNewPersonagem(Personagem p) {
    	ResponseEntity<Account> responseACC = accs.getAcc(p.getId_conta());
    	if(!(responseACC.getStatusCode() == HttpStatus.OK)) {
    		return ResponseEntity.status(422).body(new CustomErrorHandler().contaNaoExistente(p));
    	}
    	if(nomeExisteNaBase(p.getNome())) {
    		return ResponseEntity.status(422).body(new CustomErrorHandler().PersonagemNomeJaExiste(p));
    	}
    	p.setNivel(1);
    	p.setExperiencia(0);
    	return ResponseEntity.ok(pr.save(p));
    }
    private boolean nomeExisteNaBase(String nome) {
    	return pr.findByNome(nome).size() > 0;
    }
    
   public PersonagemResponse getPersonagem(Long id) throws EntityNotFoundException{
	   Personagem personagem = Optional.ofNullable(pr.findById(id).get()).orElseThrow(() -> new EntityNotFoundException("Nao foi encontrado personagem com este id: "+id));
	   List<PokemonUnico> pokemons = new ArrayList<>();
	   personagem.getHolds().forEach(
			   	idPokemon -> { 
			   		try {
						pokemons.add(Optional.ofNullable(repository.findById(idPokemon).get()).orElseThrow(() -> new EntityNotFoundException("Nao foi encontrado pokemon com este id: "+idPokemon)));
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
