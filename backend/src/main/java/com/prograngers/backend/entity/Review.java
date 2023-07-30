package com.prograngers.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="solution_id")
    private Solution solution;

    private Integer codeLineNumber;

    private Integer orderParent;

    private Integer orderChild;

    private Long parentId;

    private String mention;

    private String content;

    private LocalDate date;

    public void updateMention(String mention) {
        if (mention != null) {
            this.mention = mention;
        }
    }

        public void updateContent(String content){
            if (content!=null){
                this.content = content;
        }
    }

}
