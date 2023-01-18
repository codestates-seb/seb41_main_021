package com.yata.backend.domain.chat.service;

import com.yata.backend.domain.chat.entity.ChatEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.converter.JsonbMessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessagingScheduler {

   private SimpMessagingTemplate messagingTemplate;

   @Autowired
   public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
      this.messagingTemplate = messagingTemplate;
   }

   @KafkaListener(topics = "chat", groupId = "yata")
   public void checkNotice(ChatEntity message){
      log.info("checkNotice call");
      try{
         messagingTemplate.setMessageConverter(new StringMessageConverter());
         messagingTemplate.convertAndSend("/subscribe/notice" + message.getRoom(), message.getMessage());

      }catch(Exception ex){
         log.error(ex.getMessage());
      }
   }
}