package com.yata.backend.domain.chat.entity;

import com.yata.backend.domain.member.entity.Member;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatEntity {
    @Id
    private Long chatId;

    private String user;
    private String room;
    private String message;
    private String timestamp;
}
