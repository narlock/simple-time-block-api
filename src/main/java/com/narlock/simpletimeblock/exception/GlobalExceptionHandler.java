package com.narlock.simpletimeblock.exception;

import com.narlock.simpletimeblock.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CalendarEventNotFoundException.class)
    public ResponseEntity<ErrorResponse> calendarEventNotFoundExceptionHandler(final CalendarEventNotFoundException e) {
        log.error("CalendarEventNotFoundException caught in controller advice: {} id: {}", e.getMessage(), e.getCalendarEventId());
        return createErrorResponse(
                ErrorResponse.builder()
                        .status("404")
                        .message("Calendar Event was not found for id " + e.getCalendarEventId())
                        .detail("Please enter a calendar event id that is valid")
                        .timestamp(LocalDateTime.now().toString())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorResponse errorResponse, HttpStatus status) {
        return new ResponseEntity<>(errorResponse, status);
    }
}
