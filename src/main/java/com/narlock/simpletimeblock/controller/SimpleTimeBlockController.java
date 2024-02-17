package com.narlock.simpletimeblock.controller;

import com.narlock.simpletimeblock.model.CalendarEvent;
import com.narlock.simpletimeblock.service.SimpleTimeBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/time-block")
@RequiredArgsConstructor
public class SimpleTimeBlockController {

    private final SimpleTimeBlockService simpleTimeBlockService;

    @GetMapping("/{id}")
    public CalendarEvent getCalendarEventById(@PathVariable("id") Integer id) {
        log.info("Received request to retrieve calendar event with id {}", id);
        return simpleTimeBlockService.getCalendarEventById(id);
    }
}
