package com.narlock.simpletimeblock.service;

import com.narlock.simpletimeblock.exception.CalendarEventNotFoundException;
import com.narlock.simpletimeblock.exception.NoCalendarEventOnDayException;
import com.narlock.simpletimeblock.model.CalendarEvent;
import com.narlock.simpletimeblock.model.request.CreateCalendarEventRequest;
import com.narlock.simpletimeblock.model.request.CreateRecurringCalendarEventsRequest;
import com.narlock.simpletimeblock.model.response.RecurringCalendarEventsResponse;
import com.narlock.simpletimeblock.repository.CalendarEventRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.narlock.simpletimeblock.util.RecurringEventParser.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleTimeBlockService {
  private final CalendarEventRepository calendarEventRepository;

  /**
   * Service attempts to retrieve a calendar event from the back end database. If the CalendarEvent
   * is present it will be returned, otherwise, it is not found.
   *
   * @param id
   * @return CalendarEvent matching id if found
   */
  public CalendarEvent getCalendarEventById(Integer id) {
    Optional<CalendarEvent> calendarEventOptional = calendarEventRepository.findById(id);
    if (calendarEventOptional.isPresent()) {
      log.info("Calendar event successfully found for id {}", id);
      return calendarEventOptional.get();
    } else {
      log.error("Calendar event not found for id {}", id);
      throw new CalendarEventNotFoundException(id, "Calendar event not found");
    }
  }

  /**
   * Service will retrieve all calendar events on the back end database. If no calendar events are
   * on the back end system, an exception will be thrown to indicate as such.
   *
   * @return all calendar events on back end database
   */
  public List<CalendarEvent> getAllCalendarEvents() {
    List<CalendarEvent> calendarEvents = calendarEventRepository.findAll();
    if (!calendarEvents.isEmpty()) {
      return calendarEvents;
    } else {
      final String message = "No calendar events exist on back-end database";
      log.error(message);
      throw new RuntimeException(message);
    }
  }

  /**
   * Service will retrieve all calendar events on the back end database that match date parameter.
   * If no calendar events are on the back end system, an exception will be thrown to indicate that
   * no calendar events exist for the date.
   *
   * @param date
   * @return calendar events that match date parameter
   */
  public List<CalendarEvent> getCalendarEventsOnDay(LocalDate date) {
    List<CalendarEvent> calendarEvents = calendarEventRepository.findByDate(date);
    if (!calendarEvents.isEmpty()) {
      return calendarEvents;
    } else {
      final String message = "No calendar events were found on date " + date;
      log.error(message);
      throw new NoCalendarEventOnDayException(date, message);
    }
  }

  /**
   * Creates a calendar event
   * @param request
   * @return the created calendar event
   */
  public CalendarEvent createCalendarEvent(CreateCalendarEventRequest request) {
    return calendarEventRepository.save(request.toCalendarEvent());
  }

  /**
   * Creates a set of calendar events according to the request
   *
   * @param request
   * @return response containing amount of events created
   */
  public RecurringCalendarEventsResponse createRecurringCalendarEvents(CreateRecurringCalendarEventsRequest request) {
    List<CreateCalendarEventRequest> eventsToCreate = new ArrayList<>();

    // Parse repeat and determine the days to repeat
    List<DayOfWeek> repeatDays = parseRepeatDays(request.getRepeat());

    // Calculate the end date based on the until field
    LocalDate endDate = calculateEndDate(request.getUntil());

    // Generate recurring events
    LocalDate currentDate = request.getEvent().getDate();
    while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
      if (repeatDays.isEmpty() || repeatDays.contains(currentDate.getDayOfWeek())) {
        eventsToCreate.add(createEventFromRequest(request, currentDate));
      }
      currentDate = currentDate.plusDays(1);
    }

    // Save events to the repository
    for (CreateCalendarEventRequest eventRequest : eventsToCreate) {
      calendarEventRepository.save(eventRequest.toCalendarEvent());
    }

    // Return response with the amount of events created
    return new RecurringCalendarEventsResponse(eventsToCreate.size(), request.getEvent().getDate(), endDate, request.getRepeat());
  }
}
