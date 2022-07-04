package org.nicholasshore.astrodia.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nicholasshore.astrodia.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(HomeController.class)
@RunWith(SpringRunner.class)
public class HomeControllerTest {

    @MockBean
    private FlightService flightService;
    @MockBean
    private RegionService regionService;
    @MockBean
    private PortService portService;
    @MockBean
    private SpacelinerService spacelinerService;
    @MockBean
    private ShuttleService shuttleService;


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnViewWithPrefilledData() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("main"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("regions"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ports"));
    }
}