package com.juliano.app.servie;

import com.juliano.app.Models.Account;
import com.juliano.app.Models.AccountValidation;
import com.juliano.app.config.RespostaPadrao;
import com.juliano.app.repository.AccountValidationRepository;
import com.juliano.app.repository.AccountsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.juliano.app.builder.AccBuilder.umaConta;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith({SpringExtension.class})
class AccountServiceTest {
    @Mock
    private AccountsRepository accountsRepository;
    @MockBean
    private AccountsRepository acr;
    @Mock
    private AccountValidationRepository acvr;
    @InjectMocks
    private AccountService acs;

    @Disabled
    public void setup(){
        MockitoAnnotations.initMocks(this);
        // para mock em métodos privados é com powerMockito
        //acs = PowerMockito.spy(acs);
    }

    @Disabled
    @Test
    void deveFazerNewAcc() {
        //when(accountsRepository.save(any(Account.class))).thenReturn(umaConta().agora());
        //when(acvr.save(any(AccountValidation.class))).thenReturn(new AccountValidation());

        // com o Mockito.when estava percorrendo o método executando tudo antes dele salvar o retorno
        // com o Mockito doReturn ele salva o retorno primeiro e não percorre o método
        doReturn(umaConta().agora()).when(accountsRepository).save(any(Account.class));
        doReturn(new AccountValidation()).when(acvr).save(any(AccountValidation.class));
        RespostaPadrao respostaPadrao = acs.newAcc(umaConta().agora());
        Account a = (Account) respostaPadrao.getResponse();
        verify(accountsRepository).save(umaConta().agora());
        assertEquals("teste@teste.com", a.getEmail());
    }

    @Disabled
    @Test
    void getAcc() {
    }

    @Disabled
    @Test
    void criarPersonagem() {
    }

    @Disabled
    @Test
    void validarEmail() {
    }
}