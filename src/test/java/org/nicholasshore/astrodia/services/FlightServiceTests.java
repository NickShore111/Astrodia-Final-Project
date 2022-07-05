package org.nicholasshore.astrodia.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.nicholasshore.astrodia.models.Flight;
import org.nicholasshore.astrodia.models.Spaceliner;
import org.nicholasshore.astrodia.util.AstrodiaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
public class FlightServiceTests {
    String[] spacelinerList = null;
    String[] shuttleList = null;
    String departing = null;
    String[] departureRegionList = null;
    String[] departurePortList = null;
    String arriving = null;
    String[] arrivalRegionList = null;
    String[] arrivalPortLis = null;
    @Autowired
    FlightService flightService;

    @ParameterizedTest
    @CsvSource({
            "MO, ES, 07/04/2022, 07/08/2022",
            "MA, EO, 07/06/2022, 07/12/2022"
    })
    public void regionRoundtripSearchTest(String departureRegion, String arrivalRegion, String departureDate, String arrivalDate) {
        List<Flight> departures = flightService.searchByRegionDepartureAndDateRange(departureRegion, arrivalRegion, departureDate, arrivalDate);
        List<Flight> returns = flightService.searchByRegionArrivalAndDateRange(departureRegion, arrivalRegion, departureDate,arrivalDate);

        departures.forEach(flight -> {
            assertThat(flight.getLaunchPad().getPort().getRegion().getId()).isEqualTo(departureRegion);
            assertThat(flight.getDeparting()).isAfterOrEqualTo(new Date());
        });
    }
    @Test
    public void getAllFlightsUsingZeroSelectionCriteria() {
        List<Flight> actualFlights = flightService.findAll();

        List<Flight> expectedFlights = flightService.findFlightsBySelectionCriteria(
                spacelinerList,
                shuttleList,
                departing,
                departurePortList,
                departureRegionList,
                arriving,
                arrivalRegionList,
                arrivalPortLis );

        assertThat(expectedFlights).isNotNull();
        assertThat(actualFlights.size()).isEqualTo(expectedFlights.size());
    }

}
