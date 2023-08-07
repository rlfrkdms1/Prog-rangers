package com.prograngers.backend.entity;

import com.prograngers.backend.entity.constants.JudgeConstant;
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
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String link;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private JudgeConstant ojName;

    public void updateTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    public void updateLink(String link) {
        if (link != null) {
            this.link = link;
        }
    }

    public void updateOjName(JudgeConstant ojName) {
        if (ojName != null) {
            this.ojName = ojName;
        }
    }


}
