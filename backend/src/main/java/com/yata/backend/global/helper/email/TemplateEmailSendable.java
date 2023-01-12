package com.yata.backend.global.helper.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

import static javax.mail.Message.RecipientType.TO;

public class TemplateEmailSendable implements EmailSendable {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.host}")
    private String host;

    public TemplateEmailSendable(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(String message, String toEmail) throws MessagingException, UnsupportedEncodingException {
        // TODO 템플릿을 사용한 이메일을 보낼 수 있습니다.
        /*MimeMessage mailMessage = javaMailSender.createMimeMessage();
        mailMessage.addRecipients(TO, toEmail);
        mailMessage.setText(message);
        mailMessage.setSubject("YATA 이메일 인증 코드 입니다.");
        javaMailSender.send(mailMessage);*/
        MimeMessage messages = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(messages, true, "UTF-8");
        InternetAddress to = new InternetAddress();
        to.setAddress(toEmail);
        to.setPersonal(toEmail, "UTF-8");

        helper.setFrom(host);
        helper.setTo(to);
        helper.setSubject("YATA 이메일 인증 코드 입니다.");
        helper.setText(message, true);

        javaMailSender.send(messages);
    }
}
