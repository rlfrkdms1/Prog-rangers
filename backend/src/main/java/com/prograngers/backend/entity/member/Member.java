package com.prograngers.backend.entity.member;

import com.prograngers.backend.exception.badrequest.ProhibitionNicknameException;
import com.prograngers.backend.support.Encrypt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

    private static final String QUIT_NICKNAME = "탈퇴한 사용자";
    private static final List<String> PROHIBITED_NICKNAMES = List.of("탈퇴한 사용자");

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

    @CreatedDate
    @Column(name = "password_modified_at")
    private LocalDateTime passwordModifiedAt;

    @Column(name = "usable", nullable = false)
    private boolean usable;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Builder
    public Member(Long id, Long socialId, MemberType type, String nickname, String email, String github,
                  String introduction, String password, String photo, LocalDateTime passwordModifiedAt,
                  boolean usable) {
        validProhibitionNickname(nickname);
        this.id = id;
        this.socialId = socialId;
        this.type = type;
        this.nickname = nickname;
        this.email = email;
        this.github = github;
        this.introduction = introduction;
        this.password = password;
        this.photo = photo;
        this.passwordModifiedAt = passwordModifiedAt;
        this.usable = usable;
    }

    private void validProhibitionNickname(String nickname) {
        if (PROHIBITED_NICKNAMES.contains(nickname)) {
            throw new ProhibitionNicknameException();
        }
    }

    private void updateNickName(String nickname) {
        validProhibitionNickname(nickname);
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
            this.passwordModifiedAt = LocalDateTime.now();
        }
    }

    public void encodePassword(String password) {
        if (password != null) {
            this.password = Encrypt.encoding(password);
        }
    }

    private void updatePhoto(String photo) {
        if (photo != null) {
            this.photo = photo;
        }
    }

    public void delete() {
        this.usable = false;
    }

    public boolean isUsable() {
        return usable;
    }

    public void update(Member member) {
        updateNickName(member.getNickname());
        updateGitHub(member.getGithub());
        updateIntroduction(member.getIntroduction());
        updatePassword(member.getPassword());
        updatePhoto(member.getPhoto());
    }

    public String getNickname() {
        if (isUsable()) {
            return nickname;
        }
        return QUIT_NICKNAME;
    }
}
