package com.yata.backend.global.helper.email;

import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
public class EmailSender {
    private final EmailSendable emailSendable;

    public EmailSender(EmailSendable emailSendable) {
        this.emailSendable = emailSendable;
    }

    public void sendEmail(String message, String toEmail) throws MailSendException,
            InterruptedException, MessagingException, UnsupportedEncodingException {
        emailSendable.send(message, toEmail);
    }
}
