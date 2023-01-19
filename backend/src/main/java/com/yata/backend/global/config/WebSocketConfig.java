package com.yata.backend.global.config;

import com.yata.backend.auth.token.AuthToken;
import com.yata.backend.auth.token.AuthTokenProvider;
import com.yata.backend.auth.utils.HeaderUtil;
import com.yata.backend.global.utils.RedisUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final AuthTokenProvider authTokenProvider;
    private final RedisUtils redisUtils;

    public WebSocketConfig(AuthTokenProvider authTokenProvider, RedisUtils redisUtils) {
        this.authTokenProvider = authTokenProvider;
        this.redisUtils = redisUtils;
    }

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
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                System.out.println(accessor.getFirstNativeHeader("Authorization") + " : " + accessor.getDestination());
                System.out.println(accessor.getCommand());
                if (equalsCommand(accessor)) {
                    String stringToken = accessor.getFirstNativeHeader("Authorization");
                    stringToken = HeaderUtil.getRemovePrefixBearer(stringToken);
                    AuthToken token = authTokenProvider.convertAuthToken(stringToken);
                    if (token.validate() && !redisUtils.hasKeyBlackList(stringToken)) {
                        Authentication user = authTokenProvider.getAuthentication(token);
                        SecurityContextHolder.getContext().setAuthentication(user);
                        accessor.setUser(user);
                    }
                }
                return message;
            }
        });
    }

    private static boolean equalsCommand(StompHeaderAccessor accessor) {
        if(StompCommand.CONNECT.equals(accessor.getCommand())){
            return true;
        }else if(StompCommand.SEND.equals(accessor.getCommand())){
            return true;
        }
        return false;
    }

}
