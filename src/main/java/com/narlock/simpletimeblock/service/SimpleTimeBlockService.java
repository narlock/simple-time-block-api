package com.narlock.simpletimeblock.service;

import com.narlock.simpletimeblock.exception.CalendarEventNotFoundException;
import com.narlock.simpletimeblock.exception.NoCalendarEventOnDayException;
import com.narlock.simpletimeblock.model.CalendarEvent;
import com.narlock.simpletimeblock.repository.CalendarEventRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
