package com.yata.backend.domain.member.event;

import com.yata.backend.global.helper.email.EmailSender;
import com.yata.backend.global.helper.email.template.EmailAuthTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@Slf4j
public class EmailAuthEventListener {
   private final EmailSender emailSender;


   public EmailAuthEventListener(EmailSender emailSender) {
      this.emailSender = emailSender;
   }
   @Async
   @EventListener
   public void listen(EmailAuthApplicationEvent event) throws Exception {
      try {
         // 전송할 메시지를 생성했다고 가정.

         String message = EmailAuthTemplate.emailAuth(event.getEmailAuthDto().getAuthCode());
         emailSender.sendEmail(message , event.getEmailAuthDto().getEmail());
      } catch (MailSendException e) {
         e.printStackTrace();
      }
   }
}
