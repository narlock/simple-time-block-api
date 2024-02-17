package com.narlock.simpletimeblock.util

import com.narlock.simpletimeblock.model.CalendarEvent

import java.time.LocalDate
import java.time.LocalTime

class TestUtil {
    public static final Integer ID = 1
    public static final CalendarEvent CALENDAR_EVENT = new CalendarEvent(
            id: 1,
            name: 'Sample Event',
            note: 'Sample Note',
            startTime: LocalTime.of(10, 0),
            endTime: LocalTime.of(12, 0),
            date: LocalDate.of(2024, 2, 17),
            meta: 'Sample Meta'
    )
}
