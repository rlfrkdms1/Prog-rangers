package com.prograngers.backend.dto;

import com.prograngers.backend.exception.ErrorCode;
import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
public class ErrorResponse {
    // String description;

    ErrorCode errocode;
    String descriptions;
}
