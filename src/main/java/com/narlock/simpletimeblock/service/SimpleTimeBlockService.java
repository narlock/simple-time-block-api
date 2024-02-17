package com.narlock.simpletimeblock.service;

import com.narlock.simpletimeblock.exception.CalendarEventNotFoundException;
import com.narlock.simpletimeblock.model.CalendarEvent;
import com.narlock.simpletimeblock.repository.CalendarEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleTimeBlockService {
    private final CalendarEventRepository calendarEventRepository;

    public CalendarEvent getCalendarEventById(Integer id) {
        Optional<CalendarEvent> calendarEventOptional = calendarEventRepository.findById(id);
        if(calendarEventOptional.isPresent()) {
            log.info("Calendar event successfully found for id {}", id);
            return calendarEventOptional.get();
        } else {
            log.error("Calendar event not found for id {}", id);
            throw new CalendarEventNotFoundException(id, "Calendar event not found");
        }
    }
}
