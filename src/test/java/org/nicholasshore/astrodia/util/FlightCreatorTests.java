package org.nicholasshore.astrodia.util;


import org.junit.jupiter.api.*;
import org.nicholasshore.astrodia.models.Flight;
import org.nicholasshore.astrodia.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FlightCreatorTests {
    @Autowired
    FlightService flightService;

    @BeforeAll
    static void beforeAll() {}

    @AfterAll
    static void afterAll() {}

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    void flightsLoadedTest() {
        assertThat(flightService.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("Flight Departure and Arrival Regions Differ")
    void flightsDepartureAndArrivalRegionsTest() {
        List<Flight> allFlights = flightService.findAll();
        allFlights.forEach(flight -> {
            assertThat(flight.getLaunchPad().getPort().getRegion())
                    .isNotEqualTo(flight.getArrivalPad().getPort().getRegion());
        });
    }

    @Test
    @DisplayName("Flight Dates in Proper Sequence and Range")
    void flightsArrivalAndDepartureDatesTest() {
        Timestamp today = new Timestamp(new Date().getTime() - 86400000L);

        List<Flight> flights = flightService.findAll();
        flights.forEach(flight -> {
            assertThat(flight.getDeparting()).isBefore(flight.getArriving()).isAfter(today);
        });
    }

}
