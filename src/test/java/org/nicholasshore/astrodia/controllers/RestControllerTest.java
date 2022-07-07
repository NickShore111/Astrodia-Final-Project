package org.nicholasshore.astrodia.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nicholasshore.astrodia.models.Flight;
import org.nicholasshore.astrodia.models.Pad;
import org.nicholasshore.astrodia.models.Shuttle;
import org.nicholasshore.astrodia.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestController.class)
public class RestControllerTest {

    @Autowired
    RestController restController;

    @MockBean
    FlightService flightService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    public void setUpMockMvc() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }
    @Test
    @WithMockUser
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/astrodia/api/test")).andDo(print()).andExpect(status().isOk())
                        .andExpect(content().string(containsString("Hello, World!")));
    }

    @Test
    @WithMockUser
    public void getFlightById() throws Exception{
        long NOW = System.currentTimeMillis();
        long ONE_DAY = 86400000L;

        Timestamp TODAY = new Timestamp(NOW);
        Timestamp TOMORROW = new Timestamp(NOW+ONE_DAY);

        Flight flight = new Flight();
        flight.setFlightCode("ABC123");
        flight.setLaunchPad(new Pad());
        flight.setArrivalPad(new Pad());
        flight.setShuttle(new Shuttle());
        flight.setDeparting(TODAY);
        flight.setArriving(TOMORROW);
        flight.setPricePerSeat(2300);
        flight.setSeatsAvailable(3);

        when(flightService.findById(anyInt())).thenReturn(flight);

        mockMvc.perform(MockMvcRequestBuilders.get("/astrodia/api/flights/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flightCode").value("ABC123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pricePerSeat").value(2300))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatsAvailable").value(3))
                .andExpect(status().isOk());
    }


}
