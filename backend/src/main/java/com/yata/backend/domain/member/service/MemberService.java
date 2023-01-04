package com.yata.backend.domain.member.service;


import com.yata.backend.domain.member.entity.Member;

public interface MemberService {

    Member createMember(Member member);
    Member findMember(String email);
    Member findMember(Long id);


    Member updateMember(Member member, Member patchMemberDtoToMember);

    Member updateMemberTags(Member member, String tag, boolean isFollow);
    Member deleteMemberTags(Member member, String tag);
}
