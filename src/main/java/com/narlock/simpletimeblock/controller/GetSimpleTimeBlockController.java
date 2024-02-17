package com.narlock.simpletimeblock.controller;

import com.narlock.simpletimeblock.model.CalendarEvent;
import com.narlock.simpletimeblock.service.SimpleTimeBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/time-block")
@RequiredArgsConstructor
public class GetSimpleTimeBlockController {

    private final SimpleTimeBlockService simpleTimeBlockService;

    /**
     * Retrieve a time-blocked event
     * @param id
     * @return CalenarEvent associated to the id parameter
     */
    @GetMapping("/{id}")
    public CalendarEvent getCalendarEventById(@PathVariable("id") Integer id) {
        log.info("Received request to retrieve calendar event with id {}", id);
        return simpleTimeBlockService.getCalendarEventById(id);
    }
}
