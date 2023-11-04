package com.juliano.app.servie;

import com.juliano.app.Models.Account;
import com.juliano.app.Models.AccountValidation;
import com.juliano.app.Models.Personagem;
import com.juliano.app.accdtos.LoginDTO;
import com.juliano.app.config.Midleware;
import com.juliano.app.config.RespostaPadrao;
import com.juliano.app.exceptions.CustomNotFoundException;
import com.juliano.app.repository.AccountValidationRepository;
import com.juliano.app.repository.AccountsRepository;
import com.juliano.app.repository.PersonagemRepository;
import com.juliano.app.servie.security.PasswordUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.io.FileNotFoundException;
import java.util.Optional;

import static com.juliano.app.config.Utils.isNull;

@Service
@AllArgsConstructor
public class AccountService {

	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private PasswordUtils passwordUtils;
	
	@Autowired
	private PersonagemRepository personagemRepository;

	@Autowired
	private AccountValidationRepository accountValidationRepository;

	/***
	 * Procura a conta e se ela existir e ainda nao tiver ativada ela vai pro espaco
	 * se tiver ativada estoura uma excecao
	 */
	public RespostaPadrao newAcc(Account acc) {

		Account account = accountsRepository.findByEmail(acc.getEmail());
		
		if(account != null && account.isActived()) {
			throw new IllegalStateException("Account already exists");
		}else if(account != null && account.isActived() == false) {
			accountsRepository.delete(account);
		}

		acc.setPassword(passwordUtils.generateSecurePassword(acc.getPassword(), "mypokemongame"));
		acc.setCoin(0);
		acc.setSucoin(0);
		acc.setLevel(1);
		acc.setActived(false);
		Account a = accountsRepository.save(acc);
		if(a == null){
			return null;
		}

		int cod = GerarRandonNumber.getRandonInt(9999);
		String s = "**Código de verificaçao: "+cod+" **";
		JavaMailApp mail = new JavaMailApp();
		Boolean b = mail.sendEmail(acc.getEmail(), s);

		if(b==true){
			AccountValidation accv = new AccountValidation();
			accv.setEmail(a.getEmail());
			accv.setCode(cod);
			accv.setAcc_id(a.getId());
			accountValidationRepository.save(accv);
		}

		return RespostaPadrao.builder().mensagem(b == true ? "Enviado email de confirmação para "+a.getEmail() : "Falha ao enviar email de confirmação").status(201).response(a).build();
	}

	public RespostaPadrao findAllPageable(PageRequest pageable) {
		return RespostaPadrao.builder().response(accountsRepository.findAll(pageable)).build();
	}
	
	public ResponseEntity<Account> getAcc(Long id) {
		return accountsRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	public Personagem criarPersonagem(Long id, String nome){
		Personagem p = new Personagem();
		p.setNome(nome);
		p.setId_conta(id);
		p.setExperiencia(0);
		p.setNivel(1);
		return personagemRepository.save(p);
	}

	public ResponseEntity<RespostaPadrao> validarEmail(int cod) throws CustomNotFoundException {
		AccountValidation a = Optional.ofNullable(accountValidationRepository.findByCode(cod)).orElseThrow(() -> new CustomNotFoundException("Código inválido"));

		Account ac = accountsRepository.findByEmail(a.getEmail());
		ac.setActived(true);
		accountsRepository.save(ac);
		accountValidationRepository.delete(a);
		return ResponseEntity.ok().body(RespostaPadrao.builder().response(ac).status(200).mensagem("Conta ativada com sucesso").build());
	}

	public int subirDeNivel(Long id) {
		Account a = this.buscarConta(id);
		if(!isNull(a)) return -1;
		a.setLevel(a.getLevel()+1);
		accountsRepository.save(a);
		return a.getLevel();
	}
	public Account buscarConta(Long id){
		return accountsRepository.findById(id).map(account ->  account).orElse(null);
	}
	public ResponseEntity<Integer> salvarExperiencia(Long id, int quantidade){
		Account a = this.buscarConta(id);
		if(isNull(a)){
			ResponseEntity.notFound().build();
		}
		a.setExperience(quantidade);
		Double calc = Double.valueOf(a.getExperience()/(a.getLevel()*100));
		if(calc>1){
			subirDeNivel(id);
		}
		accountsRepository.save(a);
		return ResponseEntity.ok(a.getLevel());
	}
    public ResponseEntity login(LoginDTO login) {
		Account acc = accountsRepository.findByEmail(login.getUsername());
		if(acc==null){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RespostaPadrao.builder()
					.status(401).mensagem("Not found account for that email address").build());
		}
		return ResponseEntity.ok(RespostaPadrao.builder().status(200).mensagem("Access succeed")
				.response(Midleware.genereteJWT(acc)).build());
    }
}
