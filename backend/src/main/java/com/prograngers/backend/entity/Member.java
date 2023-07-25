package com.prograngers.backend.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String nickname;

    private String email;

    @Nullable
    private String github;

    @Nullable
    private String introduction;

    private String password;

    private String phoneNumber;



}
