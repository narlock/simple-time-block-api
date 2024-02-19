package com.narlock.simpletimeblock.model.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * The create recurring calendar events request object is the request object used for creating
 * recurring calendar events. The repeat field represents how often we want the event to repeat and
 * the until field represents how long we want the recurring event to be created.
 *
 * @author narlock
 */
@Data
@RequiredArgsConstructor
public class CreateRecurringCalendarEventsRequest {
  public String repeat;
  public String until;
  public CalendarEventRequest event;
}
