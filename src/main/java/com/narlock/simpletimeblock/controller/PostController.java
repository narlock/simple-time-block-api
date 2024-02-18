package com.narlock.simpletimeblock.controller;

import com.narlock.simpletimeblock.model.CalendarEvent;
import com.narlock.simpletimeblock.model.request.CreateCalendarEventRequest;
import com.narlock.simpletimeblock.model.request.CreateRecurringCalendarEventsRequest;
import com.narlock.simpletimeblock.model.response.RecurringCalendarEventsResponse;
import com.narlock.simpletimeblock.service.SimpleTimeBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * This is the POST controller, responsible for the POST operations on the REST API
 *
 * @author narlock
 * @version 1.0.0
 * @since 2024-02-17
 */
@Slf4j
@RestController
@RequestMapping("/time-block")
@RequiredArgsConstructor
public class PostController {

  private final SimpleTimeBlockService simpleTimeBlockService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CalendarEvent createCalendarEvent(@RequestBody CreateCalendarEventRequest request) {
    return simpleTimeBlockService.createCalendarEvent(request);
  }

  @PostMapping("/recurring")
  @ResponseStatus(HttpStatus.CREATED)
  public RecurringCalendarEventsResponse createRecurringCalendarEvents(@RequestBody CreateRecurringCalendarEventsRequest request) {
     return simpleTimeBlockService.createRecurringCalendarEvents(request);
  }
}
