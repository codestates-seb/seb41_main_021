package com.yata.backend.auth.oauth2.dto;

import lombok.Getter;

@Getter
public enum ProviderType {
    GOOGLE("google"),
    FACEBOOK("facebook"),
    NATIVE("native"),
    KAKAO("kakao");

    ProviderType(String providerType) {
        this.providerType = providerType;
    }

    private String providerType;

}
