package com.narlock.simpletimeblock.controller;

import com.narlock.simpletimeblock.model.CalendarEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time-block")
public class SimpleTimeBlockController {

    @GetMapping
    public CalendarEvent getCalendarEventById() {
        return CalendarEvent.builder().name("Hello World").build();
    }
}
