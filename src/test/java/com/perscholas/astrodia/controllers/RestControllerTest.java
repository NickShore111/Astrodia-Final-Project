package com.perscholas.astrodia.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RestController.class)
public class RestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getFlight_ShouldReturnFlight() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/astrodia/api/flights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("flightCode")
                        .value("ABC123 X1-J5"));
    }
}
