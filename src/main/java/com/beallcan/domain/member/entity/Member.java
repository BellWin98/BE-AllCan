package com.beallcan.domain.member.entity;

import com.beallcan.global.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column // 소셜 로그인 유저의 경우 비밀번호가 필요 없으므로, nullable
    private String password;

    @Column(columnDefinition = "VARCHAR(10)", nullable = false, unique = true)
    private String nickname;

    // S3에서 이미지 Url 가져오기
    @Column
    private String profileImage;

    @Column(columnDefinition = "VARCHAR(1)", nullable = false)
    private String useYn = "Y";

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String nickname, String profileImage, Role role){
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = role;
    }

    public void delete(){
        this.useYn = "N";
    }

    public void update(String email, String nickname, String profileImage) {
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
