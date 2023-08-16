package com.prograngers.backend.dto.error;

import com.prograngers.backend.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
public class ErrorResponse {
    ErrorCode errorCode;
    String description;

}
