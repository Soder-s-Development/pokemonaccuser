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

    public ResponseEntity<Personagem> setNewExperience(Long id, int ammount){
        Optional<Personagem> p = pr.findById(id);
        if(!p.isPresent()){
            ResponseEntity.notFound().build();
        }
        Personagem psng = p.get();
        psng.setExperience(ammount);
        Double calc = Double.valueOf(psng.getExperience()/(psng.getLevel()*100));
        if(calc>1){
            psng.setLevel(psng.getLevel()+1);
        }
        pr.save(psng);
        accs.salvarExperiencia(psng.getId_conta(), ammount);
        return ResponseEntity.ok(psng);
    }
    public ResponseEntity<Object> saveNewPersonagem(Personagem p) {
    	p.setExperience(0);
    	p.setLevel(1);
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
