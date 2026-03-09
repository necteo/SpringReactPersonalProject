package com.sist.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;        // nullable — 없을 수 있음
    private String name;
    private String picture;

    @Column(nullable = false)
    private String providerId;   // 소셜 고유 ID (항상 존재)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String name, String picture,
                String providerId, AuthProvider provider, Role role) {
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.providerId = providerId;
        this.provider = provider;
        this.role = role;
    }

    public Member update(String name, String picture, String email) {
        this.name = name;
        this.picture = picture;
        // 나중에 이메일 동의하면 업데이트 가능
        if (email != null) this.email = email;
        return this;
    }
}
