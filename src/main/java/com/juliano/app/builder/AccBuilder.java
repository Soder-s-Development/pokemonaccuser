package com.juliano.app.builder;

import com.juliano.app.Models.Account;

public class AccBuilder {
        private Account account;

        private AccBuilder(){}

        public static AccBuilder umaConta(){
            AccBuilder builder = new AccBuilder();
            builder.account = new Account();
            builder.account.setEmail("teste@teste.com");
            builder.account.setFirst_name("teste");
            builder.account.setPassword("123");
            builder.account.setActived(false);
            return builder;
        }
        public Account agora(){
            return account;
        }

}
