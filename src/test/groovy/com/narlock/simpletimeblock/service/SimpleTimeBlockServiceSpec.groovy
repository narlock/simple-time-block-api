package com.narlock.simpletimeblock.service

import com.narlock.simpletimeblock.exception.CalendarEventNotFoundException
import com.narlock.simpletimeblock.exception.NoCalendarEventOnDayException
import com.narlock.simpletimeblock.repository.CalendarEventRepository
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

import static com.narlock.simpletimeblock.util.TestUtil.*

class SimpleTimeBlockServiceSpec extends Specification {
    CalendarEventRepository calendarEventRepository = Mock()
    SimpleTimeBlockService simpleTimeBlockService = new SimpleTimeBlockService(calendarEventRepository)

    def 'get calendar event by id success'() {
        when:
        def calendarEvent = simpleTimeBlockService.getCalendarEventById(ID)

        then:
        1 * calendarEventRepository.findById(ID) >> Optional.of(CALENDAR_EVENT)
        0 * _
        calendarEvent
        calendarEvent.id == 1
        calendarEvent.name == 'Sample Event'
        calendarEvent.note == 'Sample Note'
        calendarEvent.startTime == LocalTime.of(10, 0)
        calendarEvent.endTime == LocalTime.of(12, 0)
        calendarEvent.date == LocalDate.of(2024, 2, 17)
        calendarEvent.meta == 'Sample Meta'
    }

    def 'get calendar event by id not found'() {
        when:
        simpleTimeBlockService.getCalendarEventById(ID)

        then:
        1 * calendarEventRepository.findById(ID) >> Optional.empty()
        0 * _
        thrown CalendarEventNotFoundException
    }

    def 'get all calendar events success'() {
        when:
        def calendarEvents = simpleTimeBlockService.getAllCalendarEvents()

        then:
        1 * calendarEventRepository.findAll() >> List.of(CALENDAR_EVENT)
        0 * _
        calendarEvents
        calendarEvents.size() == 1
        with(calendarEvents.get(0)) {
            id == 1
            name == 'Sample Event'
            note == 'Sample Note'
            startTime == LocalTime.of(10, 0)
            endTime == LocalTime.of(12, 0)
            date == LocalDate.of(2024, 2, 17)
            meta == 'Sample Meta'
        }
    }

    def 'get all calendar events empty'() {
        when:
        simpleTimeBlockService.getAllCalendarEvents()

        then:
        1 * calendarEventRepository.findAll() >> []
        0 * _
        thrown RuntimeException
    }

    def 'get calendar events on day success'() {
        when:
        def calendarEvents = simpleTimeBlockService.getCalendarEventsOnDay(VALID_DATE_LOCAL)

        then:
        1 * calendarEventRepository.findByDate(VALID_DATE_LOCAL) >> List.of(CALENDAR_EVENT)
        0 * _
        calendarEvents
        calendarEvents.size() == 1
        with(calendarEvents.get(0)) {
            id == 1
            name == 'Sample Event'
            note == 'Sample Note'
            startTime == LocalTime.of(10, 0)
            endTime == LocalTime.of(12, 0)
            date == LocalDate.of(2024, 2, 17)
            meta == 'Sample Meta'
        }
    }

    def 'get calendar events on day no events'() {
        when:
        simpleTimeBlockService.getCalendarEventsOnDay(VALID_DATE_LOCAL)

        then:
        1 * calendarEventRepository.findByDate(VALID_DATE_LOCAL) >> []
        0 * _
        thrown NoCalendarEventOnDayException
    }
}
