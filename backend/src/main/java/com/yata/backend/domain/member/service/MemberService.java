package com.yata.backend.domain.member.service;


import com.yata.backend.domain.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface MemberService {

    Member createMember(Member member);
    Member findMember(String email);
    Optional<Member> findMemberByEmail(String email);
    Member verifyMember(String email);
    void duplicateMember(String email);
    boolean duplicateEmail(String email);

    Member updateMember(String email, Member patchMemberDtoToMember);

    void checkDriver(Member member);
}
