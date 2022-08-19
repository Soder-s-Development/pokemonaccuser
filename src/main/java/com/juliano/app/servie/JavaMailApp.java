package com.juliano.app.servie;

import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailApp
{
    public Boolean sendEmail(String clienteEmail, String messageBody) {
       if(clienteEmail.isBlank()) return false;
        Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication("julianosoder1989@gmail.com",
                                "czznixrkobsknwfh");
                    }
                });

        /** Ativa Debug para sessão */
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            //set message headers
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");
            message.setFrom(new InternetAddress("julianosoder1989@gmail.com"));
            message.setSentDate(new Date());
            //Remetente

            Address[] toUser = InternetAddress //Destinatário(s) deve vir por variável
                    .parse(clienteEmail);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Enviando email de verificação");//Assunto
            message.setText(messageBody);
            /**Método para enviar a mensagem criada*/
            Transport.send(message);

            System.out.println("E-mail enviado!");
            return true;
        } catch (MessagingException e) {
            System.out.println("Erro! "+e.toString());
            return false;
        }
    }
}