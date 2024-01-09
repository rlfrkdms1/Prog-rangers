package com.prograngers.backend.dto.error;

import com.prograngers.backend.exception.ErrorCodeBefore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
public class ErrorResponse {
    ErrorCodeBefore errorCodeBefore;
    String description;

}
