package com.yata.backend.auth.oauth2.info.impl;

import com.yata.backend.auth.oauth2.dto.ProviderType;
import com.yata.backend.auth.oauth2.info.OAuth2UserInfo;

import java.util.Map;

public class FacebookOAuth2UserInfo extends OAuth2UserInfo {
   public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
      super(attributes);
   }

   @Override
   public String getId() {
      return (String) attributes.get("id");
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
      return (String) attributes.get("imageUrl");
   }

   @Override
   public ProviderType getProvider() {
      return ProviderType.FACEBOOK;
   }
}