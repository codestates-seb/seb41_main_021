package com.yata.backend.auth.oauth2.service;


import com.yata.backend.auth.dto.MemberPrincipal;
import com.yata.backend.auth.oauth2.dto.ProviderType;
import com.yata.backend.auth.oauth2.exception.OAuthProviderMissMatchException;
import com.yata.backend.auth.oauth2.info.OAuth2UserInfo;
import com.yata.backend.auth.oauth2.info.OAuth2UserInfoFactory;
import com.yata.backend.domain.image.entity.ImageEntity;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.repository.JpaMemberRepository;
import com.yata.backend.domain.member.utils.AuthoritiesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final JpaMemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        Member savedUser = memberRepository.findByEmail(userInfo.getEmail()).orElse(null);


        if (savedUser != null) {
            if (providerType != savedUser.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                                " account. Please use your " + savedUser.getProviderType() + " account to login."
                );
            }
            updateUser(savedUser, userInfo);
        } else {
            savedUser = createUser(userInfo, providerType);
        }

        return MemberPrincipal.create(savedUser, user.getAttributes());
    }

    public Member createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        Member user = Member.builder()
                .email(userInfo.getEmail())
                .password("oauth2")
                //.roles(AuthoritiesUtils.createRoles(userInfo.getEmail()))
                .providerType(providerType)
                .memberStatus(Member.MemberStatus.MEMBER_ACTIVE)
                .genders(Member.Gender.NOT_CHECKED)
                .imgUrl(new ImageEntity(UUID.randomUUID() , "OAUTH2" , userInfo.getImageUrl()))
                .name(userInfo.getName())
                .nickname(userInfo.getName())
                .fuelTank(30.0)
                .point(0L)
                .build();
        user.setRoles(AuthoritiesUtils.createAuthorities(user));
        return memberRepository.saveAndFlush(user);
    }

    public void updateUser(Member member, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !member.getName().equals(userInfo.getName())) {
            member.setName(userInfo.getName());
        }

      /*  if (userInfo.getImageUrl() != null && !member.getImgUrl().equals(userInfo.getImageUrl())) {
            member.setImgUrl(new ImageEntity(UUID.randomUUID() , "OAUTH2" , userInfo.getImageUrl()));
        }*/
    }
}