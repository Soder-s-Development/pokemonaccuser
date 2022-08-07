package com.juliano.app.servie;

import com.juliano.app.Models.AccountValidation;
import com.juliano.app.repository.AccountValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.juliano.app.Models.Account;
import com.juliano.app.Models.Personagem;
import com.juliano.app.repository.AccountsRepository;
import com.juliano.app.repository.PersonagemRepository;
import com.juliano.app.security.PasswordUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {

	@Autowired
	private AccountsRepository accr;
	
	@Autowired
	private PasswordUtils passu;
	
	@Autowired
	private PersonagemRepository pr;

	@Autowired
	private AccountValidationRepository acr;
	
	public Account newAcc(Account acc) {
		acc.setPassword(passu.generateSecurePassword(acc.getPassword(), "mypokemongame"));
		acc.setCoin(0);
		acc.setSucoin(0);
		Account a = accr.save(acc);
		if(a == null){
			return null;
		}
		int cod = GerarCod.getRandonInt(9999);
		String s = "**Código de verificaçao: "+cod+" **";
		JavaMailApp mail = new JavaMailApp();
		Boolean b = mail.sendEmail(acc.getEmail(), s);

		if(b==true){
			AccountValidation accv = new AccountValidation();
			accv.setEmail(a.getEmail());
			accv.setCode(cod);
			accv.setAcc_id(a.getId());
			acr.save(accv);
		}
		return a;
	}
	
	public ResponseEntity<Account> getAcc(Long id) {
		return accr.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	public Personagem criarPersonagem(Long id, String nome){
		Personagem p = new Personagem();
		p.setNome(nome);
		p.setId_conta(id);
		p.setHold_ids("");
		p.setPkmu_ids("");
		return pr.save(p);
	}

	public Boolean validarEmail(int cod){
		AccountValidation a = acr.findByCode(cod);
		if(a != null){
			Account ac = accr.findByEmail(a.getEmail());
			ac.setActived(true);
			accr.save(ac);
			return true;
		}
		return false;
	}

	public int subirDeNivel(Long id) {
		Account a = this.buscarConta(id);
		if(!checkNull(a)) return -1;
		a.setLevel(a.getLevel()+1);
		accr.save(a);
		return a.getLevel();
	}
	public Account buscarConta(Long id){
		return accr.findById(id).map(account ->  account).orElse(null);
	}
	public ResponseEntity<Integer> salvarExperiencia(Long id, int quantidade){
		Account a = this.buscarConta(id);
		if(checkNull(a)){
			ResponseEntity.notFound().build();
		}
		a.setExperience(quantidade);
		Double calc = Double.valueOf(a.getExperience()/(a.getLevel()*100));
		if(calc>1){
			subirDeNivel(id);
		}
		accr.save(a);
		return ResponseEntity.ok(a.getLevel());
	}

	private boolean checkNull(Account a) {
		if( a == null){
			return true;
		}
		return false;
	}
}
