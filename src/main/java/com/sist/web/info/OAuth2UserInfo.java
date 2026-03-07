package com.sist.web.info;

public interface OAuth2UserInfo {
	String getProviderId();
    String getEmail();
    String getName();
    String getPicture();
}
