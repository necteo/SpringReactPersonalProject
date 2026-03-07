package com.sist.web.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.sist.web.entity.AuthProvider;
import com.sist.web.entity.Member;
import com.sist.web.entity.Role;
import com.sist.web.info.OAuth2UserInfo;
import com.sist.web.info.OAuth2UserInfoFactory;
import com.sist.web.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(request);

        String registrationId = request.getClientRegistration().getRegistrationId();
        String userNameAttr = request.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.of(registrationId, oAuth2User.getAttributes());

        Member member = saveOrUpdate(userInfo, registrationId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                oAuth2User.getAttributes(),
                userNameAttr
        );
    }

    private Member saveOrUpdate(OAuth2UserInfo info, String provider) {
        return memberRepository.findByEmail(info.getEmail())
                .map(m -> m.update(info.getName(), info.getPicture()))
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .email(info.getEmail())
                        .name(info.getName())
                        .picture(info.getPicture())
                        .role(Role.USER)
                        .provider(AuthProvider.valueOf(provider.toUpperCase()))
                        .build()));
    }
}
