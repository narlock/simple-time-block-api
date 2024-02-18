package com.narlock.simpletimeblock.util;

import com.narlock.simpletimeblock.model.request.CreateCalendarEventRequest;
import com.narlock.simpletimeblock.model.request.CreateRecurringCalendarEventsRequest;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecurringEventParser {
  public static List<DayOfWeek> parseRepeatDays(String repeat) {
    List<DayOfWeek> repeatDays = new ArrayList<>();
    if ("DAILY".equalsIgnoreCase(repeat)) {
      // Repeat every day, return an empty list
    } else if ("WEEKLY".equalsIgnoreCase(repeat)) {
      // Repeat every week, return an empty list
    } else if ("MONTHLY".equalsIgnoreCase(repeat)) {
      // Repeat every month, return an empty list
    } else {
      // Custom repeat days
      for (char dayChar : repeat.toCharArray()) {
        repeatDays.add(parseDayOfWeek(dayChar));
      }
    }
    return repeatDays;
  }

  public static DayOfWeek parseDayOfWeek(char dayChar) {
    // Map the characters M, T, W, H, F, S, U to DayOfWeek enum values
    switch (Character.toUpperCase(dayChar)) {
      case 'M':
        return DayOfWeek.MONDAY;
      case 'T':
        return DayOfWeek.TUESDAY;
      case 'W':
        return DayOfWeek.WEDNESDAY;
      case 'H':
        return DayOfWeek.THURSDAY;
      case 'F':
        return DayOfWeek.FRIDAY;
      case 'S':
        return DayOfWeek.SATURDAY;
      case 'U':
        return DayOfWeek.SUNDAY;
      default:
        throw new IllegalArgumentException("Invalid day character: " + dayChar);
    }
  }

  public static LocalDate calculateEndDate(String until) {
    switch (until) {
      case "WEEK":
        return LocalDate.now().plusWeeks(1);
      case "MONTH":
        return LocalDate.now().plusMonths(1);
      default:
        if (until.matches("\\d+MONTH")) {
          int monthsToAdd = Integer.parseInt(until.substring(0, until.indexOf("MONTH")));
          return LocalDate.now().plusMonths(monthsToAdd);
        } else if ("YEAR".equalsIgnoreCase(until)) {
          return LocalDate.now().plusYears(1);
        } else {
          throw new IllegalArgumentException("Invalid 'until' value: " + until);
        }
    }
  }

  public static CreateCalendarEventRequest createEventFromRequest(
      CreateRecurringCalendarEventsRequest request, LocalDate date) {
    CreateCalendarEventRequest eventRequest = new CreateCalendarEventRequest();
    eventRequest.setName(request.getEvent().getName());
    eventRequest.setNote(request.getEvent().getNote());
    eventRequest.setStartTime(request.getEvent().getStartTime());
    eventRequest.setEndTime(request.getEvent().getEndTime());
    eventRequest.setDate(date);
    eventRequest.setMeta(request.getEvent().getMeta());
    return eventRequest;
  }
}
