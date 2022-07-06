package org.nicholasshore.astrodia.controllers;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.validator.ValidateWith;
import org.nicholasshore.astrodia.dto.SearchDto;
import org.nicholasshore.astrodia.models.Flight;
import org.nicholasshore.astrodia.models.Pad;
import org.nicholasshore.astrodia.models.Region;
import org.nicholasshore.astrodia.models.Shuttle;
import org.nicholasshore.astrodia.security.AppSecurityConfiguration;
import org.nicholasshore.astrodia.security.AppUserDetailsService;
import org.nicholasshore.astrodia.security.LoginFailureHandler;
import org.nicholasshore.astrodia.services.*;
import org.nicholasshore.astrodia.util.AstrodiaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
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
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    @WithMockUser
    void shouldLoadMainPage() throws Exception{
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("main"));
    }
}