package com.yata.backend.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.yata.backend.auth.oauth2.dto.ProviderType;
import com.yata.backend.domain.image.entity.ImageEntity;
import com.yata.backend.domain.member.dto.MemberDto;
import com.yata.backend.global.audit.Auditable;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(indexes = {
        @Index(name = "idx_member_email", columnList = "email", unique = true),
        @Index(name = "idx_member_nickname", columnList = "nickname")
})
@Cacheable
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "email")
public class Member extends Auditable  {

    @Id
    @Column(nullable = false, updatable = false, unique = true, length = 100) // 이메일 식별자
    private String email;
    @Column(nullable = false, length = 100)
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
    @org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AuthoritiesEntity> roles;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
    private ImageEntity imgUrl;
    @Column // 차량 이미지
    private String carImgUrl;

    @Column(nullable = false) // 연료탱크
    @ColumnDefault("30")
    private Double fuelTank = 30.0;

    @Column(nullable = false) // 포인트
    @ColumnDefault("0")
    private Long point;

    // TODO phoneNumbers add

    public void payPoint(Long point){
        this.point -= point;
    }
    public void earnPoint(Long point){
        this.point += point;
    }

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
        PASSENGER,
        ADMIN,
    }

    public enum Gender {
        MAN("남자"),
        NOT_CHECKED("미선택"),
        WOMAN("여자");

        Gender(String gender) {
            this.gender = gender;
        }

        @Getter
        private String gender;
    }
    public  MemberDto.Response toResponseDto(){
        return MemberDto.Response.builder()
                .roles(roles.stream().map(role -> role.getRole().name()).collect(Collectors.toList()))
                .email(email)
                .name(name)
                .genders(genders)
                .imgUrl(imgUrl != null ? imgUrl.getUrl() : null)
                .carImgUrl(carImgUrl)
                .point(point)
                .fuelTank(fuelTank)
                .memberStatus(memberStatus)
                .nickname(nickname)
                .providerType(providerType)
                .build();

    }
}
