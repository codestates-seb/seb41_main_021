package com.yata.backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
       registry
               .addEndpoint("/stomp")
               .setAllowedOriginPatterns("*")
               .withSockJS()
               .setStreamBytesLimit(512 * 1024)
               .setHttpMessageCacheSize(1000)
               .setDisconnectDelay(30 * 1000);
       registry.addEndpoint("/websocket").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
       registry.enableSimpleBroker("/topic"); // broker 역할 수행시 사용할 prefix
       registry.enableSimpleBroker("/subscribe", "/topic");
       registry.setApplicationDestinationPrefixes("/app"); // 메세지 수신 용 prefix

    }
}
