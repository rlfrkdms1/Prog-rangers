package com.prograngers.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String link;

    @Enumerated(EnumType.STRING)
    private Judges ojName;

    public void updateTitle(String title){
        if (title!=null){
            this.title=title;
        }
    }
    public  void updateLink(String link){
        if (link!=null){
            this.link=link;
        }
    }
    public void updateOjName(Judges ojName){
        if (ojName!=null){
            this.ojName=ojName;
        }
    }


}
