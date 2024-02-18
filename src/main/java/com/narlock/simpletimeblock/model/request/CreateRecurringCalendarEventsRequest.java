package com.narlock.simpletimeblock.model.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateRecurringCalendarEventsRequest {
    public String repeat;
    public String until;
    public CreateCalendarEventRequest event;
}
