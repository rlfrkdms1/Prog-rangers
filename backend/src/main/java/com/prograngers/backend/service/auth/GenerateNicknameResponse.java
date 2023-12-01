package com.prograngers.backend.service.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class GenerateNicknameResponse {

    @JsonProperty("words")
    private List<String> nicknames;

    public String getNickname() {
        return nicknames.get(0);
    }

}
