package com.yata.backend.domain.member.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yata.backend.global.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuthoritiesEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_email")
    @JsonSerialize
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Member member;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Member.MemberRole role;

    public AuthoritiesEntity() {

    }

    public AuthoritiesEntity(Member member, String role) {
        this.member = member;
        this.role = Member.MemberRole.valueOf(role);
    }


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
