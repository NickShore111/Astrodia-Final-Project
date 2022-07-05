package org.nicholasshore.astrodia.controllers;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.nicholasshore.astrodia.models.Flight;
import org.nicholasshore.astrodia.models.Pad;
import org.nicholasshore.astrodia.models.Shuttle;
import org.nicholasshore.astrodia.security.AppSecurityConfiguration;
import org.nicholasshore.astrodia.security.AppUserDetailsService;
import org.nicholasshore.astrodia.security.LoginFailureHandler;
import org.nicholasshore.astrodia.services.*;
import org.nicholasshore.astrodia.util.AstrodiaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppSecurityConfiguration.class)
@WebMvcTest(HomeController.class)
@RunWith(SpringRunner.class)
public class HomeControllerTest {
    long NOW = System.currentTimeMillis();
    long ONE_DAY = 86400000L;

    Timestamp TODAY = new Timestamp(NOW);
    Timestamp TOMORROW = new Timestamp(NOW+ONE_DAY);
    Timestamp ONE_WEEK_FROM_TODAY = new Timestamp(NOW + (ONE_DAY * 7));
    final String FLIGHT_CODE1 = "ABC123 X1-V2";
    final Pad LAUNCH_PAD1 = AstrodiaData.PADS.get(0);
    final Pad ARRIVAL_PAD1 = AstrodiaData.PADS.get(AstrodiaData.PADS.size()-1);
    final Shuttle SHUTTLE1 = AstrodiaData.SHUTTLES.get(1);
    Flight flight = Flight.builder()
            .flightCode(FLIGHT_CODE1)
            .departing(TODAY)
            .arriving(TOMORROW)
            .seatsAvailable(3)
            .launchPad(LAUNCH_PAD1)
            .arrivalPad(ARRIVAL_PAD1)
            .shuttle(SHUTTLE1).build();
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
    @MockBean
    AppUserDetailsService appUserDetailsService;
    @MockBean
    LoginFailureHandler loginFailureHandler;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUpMockMvc() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    public void shouldLoadMainPage() throws Exception{
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("main"));
    }

//    @Test
//    public void shouldReturnListOfFlights() throws Exception{
//        when(flightService.searchByPortDepartureAndDateRange("MO","MA","07/04/2022","07/05/2022"))
//                .thenReturn(List.of(flight));
//
//        this.mockMvc
//                .perform(MockMvcRequestBuilders
//                    .get("/port-search"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].flightCode").value(FLIGHT_CODE1));
//
//    }

}