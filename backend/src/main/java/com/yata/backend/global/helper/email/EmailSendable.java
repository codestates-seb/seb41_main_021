package com.yata.backend.global.helper.email;

import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Component
public interface EmailSendable {
    void send(String message , String toEmail) throws InterruptedException, MessagingException, UnsupportedEncodingException;
}
