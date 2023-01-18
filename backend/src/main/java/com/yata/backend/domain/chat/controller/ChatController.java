package com.yata.backend.domain.chat.controller;

import com.yata.backend.domain.chat.entity.ChatEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@Validated
//@RequestMapping("/api/v1/yata/chats")
@Slf4j
public class ChatController {
    @Autowired
    private KafkaTemplate<String, ChatEntity> kafkaTemplate;

    @Transactional
    @MessageMapping("/message")
    @SendTo("/topic/greetings")
    public void greeting(ChatEntity message) throws Exception {
        log.info("message received, message:{}", message.toString());
        // 지금 시간을 넣어서 발송
        message.setTimestamp(LocalDateTime.now().toString());
        // RDS에 데이터 입력
        //int insert = chattingMapper.insertChatting(message);
        // 정상적으로 데이터가 입력된 경우

        // 카프카에 메세지를 push
        kafkaTemplate.send("chat", message).get();
    }
}
