package com.narlock.simpletimeblock.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.databind.SerializationFeature
import com.narlock.simpletimeblock.exception.CalendarEventNotFoundException
import com.narlock.simpletimeblock.exception.response.ErrorResponse;
import com.narlock.simpletimeblock.model.CalendarEvent
import com.narlock.simpletimeblock.service.SimpleTimeBlockService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

import static com.narlock.simpletimeblock.util.TestUtil.*

@SpringBootTest
@AutoConfigureMockMvc
class GetSimpleTimeBlockControllerSpec extends Specification {
    @Autowired
    MockMvc mockMvc

    @SpringBean
    SimpleTimeBlockService simpleTimeBlockService = Mock()

    ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        // We configure the object mapper this way so that we can properly serialize the
        // response bodies into LocalTime
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
    }

    def 'get calendar item by id success'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.get("/time-block/$ID")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn()

        then:
        result
        result.response.status == 200
        1 * simpleTimeBlockService.getCalendarEventById(ID) >> CALENDAR_EVENT
        0 * _

        and:
        def responseEvent = objectMapper.readValue(result.response.getContentAsString(), CalendarEvent)
        responseEvent
        responseEvent.id == 1
        responseEvent.name == 'Sample Event'
        responseEvent.note == 'Sample Note'
        responseEvent.startTime == LocalTime.of(10, 0)
        responseEvent.endTime == LocalTime.of(12, 0)
        responseEvent.date == LocalDate.of(2024, 2, 17)
        responseEvent.meta == 'Sample Meta'
    }

    def 'get calendar item by id not found'() {
        when:
        def result = mockMvc.perform(
                MockMvcRequestBuilders.get("/time-block/$ID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result
        result.response.status == 404
        1 * simpleTimeBlockService.getCalendarEventById(ID) >> { throw new CalendarEventNotFoundException(ID, "Calendar event not found") }
        0 * _

        and:
        def errorResponse = objectMapper.readValue(result.response.getContentAsString(), ErrorResponse)
        errorResponse
        errorResponse.status == '404'
        errorResponse.message == 'Calendar Event was not found for id 1'
        errorResponse.detail == 'Please enter a calendar event id that is valid'
        errorResponse.timestamp
    }
}
