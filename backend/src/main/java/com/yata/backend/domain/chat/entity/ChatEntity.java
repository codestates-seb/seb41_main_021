package com.yata.backend.domain.chat.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.global.audit.Auditable;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatEntity extends Auditable {
    @Id
    private Long chatId;

    @ManyToOne
    private Member fromMember;
    private String message;

    @ManyToOne
    private ChatRoomEntity chatRoom;


}
