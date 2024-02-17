package com.narlock.simpletimeblock.exception.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ErrorResponse {
    private String status;
    private String message;
    private String detail;
    private String timestamp;
    private final String documentation = "http://github.com/narlock/simple-time-block-api";
}
