package com.juliano.app.servie;

import com.juliano.app.Models.Account;
import com.juliano.app.Models.Personagem;
import com.juliano.app.repository.PersonagemRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

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
}
