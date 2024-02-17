package com.narlock.simpletimeblock.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarEvent {
    private int id;
    private String name;
    private String note;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String meta;
}
