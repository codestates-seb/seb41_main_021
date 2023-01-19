package com.yata.backend.domain.chat.dto;

import com.yata.backend.domain.chat.entity.ChatEntity;
import com.yata.backend.domain.chat.entity.ChatRoomEntity;
import com.yata.backend.domain.member.entity.Member;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private Long chatRoomId;
    private String message;
    private String fromMember;


    public static ChatEntity toEntity(ChatDto chatDto){
        return ChatEntity.builder()
                .chatRoom(ChatRoomEntity.builder().chatRoomId(chatDto.getChatRoomId()).build())
                .fromMember(Member.builder().email(chatDto.getFromMember()).build())
                .message(chatDto.getMessage())
                .build();
    }
}