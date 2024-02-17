package com.narlock.simpletimeblock.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class SimpleTimeBlockControllerSpec extends Specification {
    @Autowired
    MockMvc mockMvc

    def 'should return hello world'() {
        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/time-block").contentType(MediaType.APPLICATION_JSON)).andReturn()

        then:
        result
        result.response.status == 200
    }
}
