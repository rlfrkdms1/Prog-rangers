package com.prograngers.backend.entity.member;

import com.prograngers.backend.support.Encrypt;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
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
import java.time.LocalDate;


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
    @Column(nullable = false)
    private MemberType type;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    private String email;

    private String github;

    private String introduction;

    private String password;

    private String photo;

    @Column(name = "current_modified_at")
    private LocalDate currentModifiedAt;

    private void updateNickName(String nickname) {
        if (nickname != null) {
            this.nickname = nickname;
        }
    }

    public void updateRandomNickname(String nickname) {
        if (nickname != null) {
            this.nickname = nickname;
        }
    }

    private void updateGitHub(String github) {
        if (github != null) {
            this.github = github;
        }
    }

    private void updateIntroduction(String introduction) {
        if (introduction != null) {
            this.introduction = introduction;
        }
    }

    private void updatePassword(String password) {
        if (password != null) {
            this.password = Encrypt.encoding(password);
            this.currentModifiedAt = LocalDate.now();
        }
    }

    public void encodePassword(String password) {
        if (password != null) {
            this.password = Encrypt.encoding(password);
        }
    }

    private void updatePhoto(String photo){
        if (photo!=null){
            this.photo = photo;
        }
    }

    public void update(Member member) {
        updateNickName(member.getNickname());
        updateGitHub(member.getGithub());
        updateIntroduction(member.getIntroduction());
        updatePassword(member.getPassword());
        updatePhoto(member.getPhoto());
    }
}
