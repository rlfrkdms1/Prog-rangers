package com.prograngers.backend.dto;

import com.prograngers.backend.exception.Errorcode;
import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
public class ErrorResponse {
   // String description;

    Errorcode errocode;
    String descriptions;
}
