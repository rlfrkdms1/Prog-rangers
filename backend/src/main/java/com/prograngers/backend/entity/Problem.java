package com.prograngers.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String link;

    private String ojName;

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
    public void updateOjName(String ojName){
        if (ojName!=null){
            this.ojName=ojName;
        }
    }


}
