package com.yata.backend.domain.member.service;

import com.yata.backend.auth.oauth2.dto.ProviderType;
import com.yata.backend.domain.member.dto.DriverAuthDto;
import com.yata.backend.domain.member.dto.MemberDto;
import com.yata.backend.domain.member.entity.AuthoritiesEntity;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.repository.JpaMemberRepository;
import com.yata.backend.domain.member.utils.AuthoritiesUtils;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import com.yata.backend.global.utils.CustomBeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    private final SignUpVerifyService signUpVerifyService;


    public MemberServiceImpl(JpaMemberRepository memberRepository, PasswordEncoder passwordEncoder, CustomBeanUtils customBeanUtils, SignUpVerifyService signUpVerifyService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.customBeanUtils = customBeanUtils;
        this.signUpVerifyService = signUpVerifyService;
    }

    @Override
    public Member createMember(Member member) {
        duplicateMember(member.getEmail());
        signUpVerifyService.verifyEmail(member.getEmail());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        member.setProviderType(ProviderType.NATIVE);
        member.setFuelTank(30.0);// 기본 값 30
        member.setPoint(0L);// 기본 값 0
        //member.setRoles(AuthoritiesUtils.createRoles(member.getEmail()));
        member.setRoles(AuthoritiesUtils.createAuthorities(member));
        return memberRepository.save(member);
    }

    @Override
    public Member findMember(String email) {
        return verifyMember(email);
    }
    @Override
    @Cacheable(value = "memberDto", key = "#email")
    public MemberDto.Response findMemberDto(String email) {
        return findMember(email).toResponseDto();
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // Private Methods 로 바꿀 것
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
    public boolean duplicateEmail(String email) {
        return memberRepository.findByEmail(email).isEmpty();
    }

    @Override
    public Member updateMember(String email, Member patchMemberDtoToMember) {
        Member member = verifyMember(email);
        customBeanUtils.copyNonNullProperties(patchMemberDtoToMember, member);
        updateMemberCache(member);
        return member;
    }
    @CachePut(value = "memberDto", key = "#member.email")
    public MemberDto.Response updateMemberCache(Member member) {
        return member.toResponseDto();
    }

    @Override
    public void checkDriver(Member member) {
        member
                .getRoles()
                .stream()
                .filter(role -> role.getRole().name().equals(Member.MemberRole.DRIVER.name()))
                .findAny()
                .orElseThrow(() -> new CustomLogicException(ExceptionCode.MEMBER_NOT_DRIVER));
    }
    @Override
    public void verifyDriverLicense(String email, DriverAuthDto driverAuthDto) {
        // TODO Auto-generated method stub 추후 비지니스화 하면 구현 할 로직 OPEN API 활용
        Member member = findMember(email);
        if (member.getRoles().stream().anyMatch(role -> role.getRole().name().equals(Member.MemberRole.DRIVER.name()))) {
            return;
        }
        member.getRoles().add(new AuthoritiesEntity(member, Member.MemberRole.DRIVER.name()));
        updateMemberCache(member);
    }

}