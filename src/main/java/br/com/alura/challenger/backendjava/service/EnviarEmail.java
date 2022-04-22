package br.com.alura.challenger.backendjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmail {
    @Autowired private JavaMailSender mailSender;

    public void enviar(String emailDestino, String senhaGerada) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDestino);
        message.setFrom("no-reply@mandrillapp.com");
        message.setText("Estamos enviando sua senha gerada automaticamente: "+senhaGerada);
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
