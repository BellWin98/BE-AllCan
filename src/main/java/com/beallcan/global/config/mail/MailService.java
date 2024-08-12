package com.beallcan.global.config.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;

    @Async
    public void sendMail(final String receiver, final String subject, String contents) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getUsername());
        mailMessage.setTo(receiver);
        mailMessage.setSubject(subject);
        mailMessage.setText(contents);

        javaMailSender.send(mailMessage);
    }
}
