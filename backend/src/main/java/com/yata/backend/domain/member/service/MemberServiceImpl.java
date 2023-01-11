package com.yata.backend.domain.member.service;

import com.yata.backend.auth.oauth2.dto.ProviderType;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.repository.JpaMemberRepository;
import com.yata.backend.domain.member.utils.AuthoritiesUtils;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import com.yata.backend.global.utils.CustomBeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
   private final JpaMemberRepository memberRepository;
   private final PasswordEncoder passwordEncoder;
   private final CustomBeanUtils customBeanUtils;


   public MemberServiceImpl(JpaMemberRepository memberRepository, PasswordEncoder passwordEncoder, CustomBeanUtils customBeanUtils) {
      this.memberRepository = memberRepository;
      this.passwordEncoder = passwordEncoder;
      this.customBeanUtils = customBeanUtils;
   }

   @Override
   public Member createMember(Member member) {
      duplicateMember(member.getEmail());
      member.setPassword(passwordEncoder.encode(member.getPassword()));
      member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
      member.setProviderType(ProviderType.NATIVE);
      member.setFuelTank(30.0);// 기본 값 30
      member.setPoint(0L);// 기본 값 0
      member.setRoles(AuthoritiesUtils.createRoles(member.getEmail()));
      return memberRepository.save(member);
   }

   @Override
   public Member findMember(String email) {
      return verifyMember(email);
   }

   @Override
   public Optional<Member> findMemberByEmail(String email) {
      return Optional.empty();
   }

   @Override
   public Member verifyMember(String email) {
      return memberRepository.findByEmail(email).orElseThrow(() -> new CustomLogicException(ExceptionCode.MEMBER_NONE));
   }

   @Override
   public void duplicateMember(String email) {
      memberRepository.findByEmail(email)
              .ifPresent(member -> {
                 throw new CustomLogicException(ExceptionCode.MEMBER_DUPLICATE);
              });
   }

   @Override
   public Member updateMember(String email, Member patchMemberDtoToMember) {
        Member member = verifyMember(email);
        customBeanUtils.copyNonNullProperties(patchMemberDtoToMember, member);
        return member;
   }
}