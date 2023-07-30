package com.prograngers.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="solution_id")
    private Solution solution;
    private Integer orderParent;
    private Integer orderChild;
    private String mention;

    private String content;

    private LocalDate date;

    private Long parentId;

    private Integer groupNumber;

    private boolean fixed;

    public void updateSolution(Solution solution){
        this.solution =solution;
    }

    public void updateMention(String mention){
        if (mention!=null){
            this.mention = mention;
        }
    }

    public void updateContent(String content){
        if (content!=null){
            this.content = content;
        }
    }

    public void updateFixed(boolean fixed){
        this.fixed = fixed;
    }

}
