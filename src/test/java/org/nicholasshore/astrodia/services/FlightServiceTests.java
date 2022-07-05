package org.nicholasshore.astrodia.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.nicholasshore.astrodia.models.Flight;
import org.nicholasshore.astrodia.models.Spaceliner;
import org.nicholasshore.astrodia.util.AstrodiaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
@Transactional //handle Hibernate.LazyInitializeException
@PropertySource("classpath:application-mariadb.properties")
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
    @MethodSource("regionRoundtripParameterProvider")
    public void regionRoundtripSearchTest(String departureRegionId, String arrivalRegionId, String departureDate, String arrivalDate) {
        List<Flight> departures = flightService.searchByRegionDepartureAndDateRange(departureRegionId, arrivalRegionId, departureDate, arrivalDate);
        List<Flight> returns = flightService.searchByRegionArrivalAndDateRange(departureRegionId, arrivalRegionId, departureDate,arrivalDate);

        departures.forEach(flight -> {
            assertThat(flight.getLaunchPad().getPort().getRegion().getId()).isEqualTo(departureRegionId);
            assertThat(flight.getDeparting()).isAfterOrEqualTo(new Date());

            assertThat(flight.getArrivalPad().getPort().getRegion().getId()).isEqualTo(arrivalRegionId);
        });

        returns.forEach(flight -> {
            assertThat(flight.getLaunchPad().getPort().getRegion().getId()).isEqualTo(departureRegionId);
            assertThat(flight.getDeparting()).isAfterOrEqualTo(new Date());

            assertThat(flight.getArrivalPad().getPort().getRegion().getId()).isEqualTo(arrivalRegionId);
        });
    }

    static Stream<Arguments> regionRoundtripParameterProvider() {
        return Stream.of(
                arguments("MO", "ES", "07/08/2022", "07/14/2022"),
                arguments("MA", "EO", "07/08/2022", "07/14/2022"),
                arguments("ES", "MA", "07/08/2022", "07/16/2022")
        );
    }
    @Test
    public void getAllFlightsUsingZeroSelectionCriteriaTest() {
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
