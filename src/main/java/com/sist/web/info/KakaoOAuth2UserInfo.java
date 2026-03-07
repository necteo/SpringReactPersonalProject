package com.sist.web.info;

import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override public String getProviderId() { return String.valueOf(attributes.get("id")); }
    @Override public String getEmail() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        return account != null ? (String) account.get("email") : null;
    }
    @Override public String getName() {
        Map<String, Object> profile = (Map<String, Object>) attributes.get("properties");
        return profile != null ? (String) profile.get("nickname") : null;
    }
    @Override public String getPicture() {
        Map<String, Object> profile = (Map<String, Object>) attributes.get("properties");
        return profile != null ? (String) profile.get("profile_image") : null;
    }
}
