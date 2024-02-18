package com.narlock.simpletimeblock.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RecurringCalendarEventsResponse {
    private int size;
    private LocalDate startDate;
    private LocalDate endDate;
    private String repeat;
}
