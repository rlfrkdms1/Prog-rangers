package com.prograngers.backend.service.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GenerateNicknameResponse {

    @JsonProperty("words")
    private List<String> nicknames;

    public String getNickname(){
        return nicknames.get(0);
    }

}
