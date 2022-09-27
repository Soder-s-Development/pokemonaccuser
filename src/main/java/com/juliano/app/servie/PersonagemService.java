package com.juliano.app.servie;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.juliano.app.Models.Account;
import com.juliano.app.Models.Personagem;
import com.juliano.app.config.CustomErrorHandler;
import com.juliano.app.repository.PersonagemRepository;

@Service
public class PersonagemService {
    @Autowired
    PersonagemRepository pr;
    @Autowired
    AccountService accs;

    public ResponseEntity<Object> saveNewPersonagem(Personagem p) {
    	ResponseEntity<Account> responseACC = accs.getAcc(p.getId_conta());
    	if(!(responseACC.getStatusCode() == HttpStatus.OK)) {
    		return ResponseEntity.status(422).body(new CustomErrorHandler().contaNaoExistente(p));
    	}
    	if(nomeExisteNaBase(p.getNome())) {
    		return ResponseEntity.status(422).body(new CustomErrorHandler().PersonagemNomeJaExiste(p));
    	}
    	return ResponseEntity.ok(pr.save(p));
    }
    private boolean nomeExisteNaBase(String nome) {
    	return pr.findByNome(nome).size() > 0;
    }
}
