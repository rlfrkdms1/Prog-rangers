package com.prograngers.backend.entity.member;

import com.prograngers.backend.support.Encrypt;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long socialId;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    private String nickname;

    private String email;

    @Nullable
    private String github;

    @Nullable
    private String introduction;

    private String password;

    private String photo;

    public void updateNickName(String nickname) {
        if (nickname != null) {
            this.nickname = nickname;
        }
    }

    public void createRandomNickname(String nickname) {
        if (nickname != null) {
            this.nickname = nickname;
        }
    }

    public void updateGitHub(String github) {
        if (github != null) {
            this.github = github;
        }
    }

    public void updateIntroduction(String introduction) {
        if (introduction != null) {
            this.introduction = introduction;
        }
    }

    public void updatePassword(String password) {
        if (password != null) {
            this.password = Encrypt.encoding(password);
        }
    }

    public void encodePassword(String password) {
        if (password != null) {
            this.password = Encrypt.encoding(password);
        }
    }
    public void updatePhoto(String photo){
        if (photo!=null){
            this.photo = photo;
        }
    }

}
