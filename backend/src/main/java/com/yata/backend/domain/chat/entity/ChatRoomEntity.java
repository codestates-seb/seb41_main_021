package com.yata.backend.domain.chat.entity;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.entity.Yata;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "yata_id")
    private Yata yata;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "chatRoom")
    private List<ChatEntity> chats;
}
