package com.yata.backend.auth.oauth2.info.impl;




import com.yata.backend.auth.oauth2.dto.ProviderType;
import com.yata.backend.auth.oauth2.info.OAuth2UserInfo;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {
    private static final ProviderType provider = ProviderType.GOOGLE;
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    public ProviderType getProvider() {
        return provider;
    }
}
