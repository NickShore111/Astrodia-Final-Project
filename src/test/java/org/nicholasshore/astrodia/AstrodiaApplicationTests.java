package org.nicholasshore.astrodia;

import org.junit.jupiter.api.*;
import org.nicholasshore.astrodia.models.Flight;
import org.nicholasshore.astrodia.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AstrodiaApplicationTests {

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
	void flightDestinationAndArrivalDifferentTest() {
		List<Flight> allFlights = flightService.findAll();
		allFlights.forEach(flight -> {
			assertThat(flight.getLaunchPad().getPort().getRegion())
					.isNotEqualTo(flight.getArrivalPad().getPort().getRegion());
		});
	}
}
