package com.yata.backend.domain.member.entity;

import com.yata.backend.global.audit.Auditable;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends Auditable {

    @Id
    @Column(nullable = false,updatable = false, unique = true, length = 100)
    private String email;
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 20)
    private String displayName;


    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;


    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
    public enum MemberRole {
        ROLE_USER,
        ROLE_ADMIN
    }

}
