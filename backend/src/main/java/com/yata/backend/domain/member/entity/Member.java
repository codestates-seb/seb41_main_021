package com.yata.backend.domain.member.entity;

import com.yata.backend.auth.oauth2.dto.ProviderType;
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
    @Column(nullable = false,updatable = false, unique = true, length = 100) // 이메일 식별자
    private String email;
    @Column(nullable = false, length = 100) // 기본 로그인을 위한 패스워드
    private String password;
    @Column(nullable = false, length = 100) // 이름
    private String name;
    @Column(nullable = false, length = 50) // 보여질 닉네임
    private String nickname;
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false) // 성별
    private Gender genders;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false) // Oauth2 제공자
    private ProviderType providerType;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @ElementCollection(fetch = FetchType.EAGER) // 권한 목록
    private List<String> roles;

    @Column// 프로필 이미지
    private String imgUrl;
    @Column // 차량 이미지
    private String carImgUrl;

    @Column(nullable = false) // 연료탱크
    @ColumnDefault("30")
    private double fuelTank = 30;

    @Column(nullable = false) // 포인트
    @ColumnDefault("0")
    private long point;

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
        DRIVER,
        PASSANGER,
        ADMIN,
    }
    public enum Gender{
        MAN("남자"),
        NOT_CHECKED("미선택"),
        WOMAN("여자");

        Gender(String gender) {
            this.gender = gender;
        }
        @Getter
        private String gender;
    }

}
