package com.prograngers.backend.entity;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

<<<<<<< HEAD:backend/src/main/java/com/prograngers/backend/entity/Review.java
import java.time.LocalDateTime;
=======
import static com.prograngers.backend.entity.review.ReviewStatusConstant.*;
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1:backend/src/main/java/com/prograngers/backend/entity/review/Review.java

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "solution_id",nullable = false)
    private Solution solution;

    @Column(nullable = false)
    private Integer codeLineNumber;

    private Long parentId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
<<<<<<< HEAD:backend/src/main/java/com/prograngers/backend/entity/Review.java
=======

    @Column(nullable = false, name = "status")
    @Enumerated(value = EnumType.STRING)
    private ReviewStatusConstant status;

    public void update(String content){
        this.content = content;
        this.status = FIXED;
    }

    public void delete() {
        this.content = DELETED_CONTENT;
        this.status = DELETED;
    }
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1:backend/src/main/java/com/prograngers/backend/entity/review/Review.java
}
