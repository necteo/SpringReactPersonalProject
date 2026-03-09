package com.sist.web.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String token;

    private LocalDateTime createdAt;

    @Builder
    public RefreshToken(Member member, String token) {
        this.member = member;
        this.token = token;
        this.createdAt = LocalDateTime.now();
    }

    public RefreshToken updateToken(String newToken) {
        this.token = newToken;
        this.createdAt = LocalDateTime.now();
        return this;
    }
}
