package com.perscholas.astrodia.repositories;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.perscholas.astrodia.models.*;
import com.perscholas.astrodia.services.FlightService;
import com.perscholas.astrodia.util.AstrodiaData;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DataJpaTest
@Rollback(value = false)
class FlightRepositoryTests {
    FlightRepository flightRepo;
    FlightService flightService;
    SpacelinerRepository spacelinerRepo;
    ShuttleRepository shuttleRepo;
    RegionRepository regionRepo;
    PortRepository portRepo;
    PadRepository padRepo;


    final Spaceliner SPACELINER = AstrodiaData.SPACELINERS.get(0);
    final Shuttle SHUTTLE = AstrodiaData.SHUTTLES.get(0);
    final Region REGION = AstrodiaData.REGIONS.get(0);
    final Port PORT = AstrodiaData.PORTS.get(0);
    final Pad DEP_PAD = AstrodiaData.PADS.get(0);
    final Pad ARR_PAD = AstrodiaData.PADS.get(1);
    final String FLIGHT_CODE = "ABC123 X1-X2";
    long NOW = System.currentTimeMillis();
    long ONE_DAY = 86400000L;

    Timestamp TODAY = new Timestamp(NOW);
    Timestamp TOMORROW = new Timestamp(NOW+ONE_DAY);
    @Autowired
    public FlightRepositoryTests(FlightRepository flightRepo,
                                 FlightService flightService,
                                 SpacelinerRepository spacelinerRepo,
                                 ShuttleRepository shuttleRepo,
                                 RegionRepository regionRepo,
                                 PortRepository portRepo,
                                 PadRepository padRepo) {
        this.flightRepo = flightRepo;
        this.flightService = flightService;
        this.spacelinerRepo = spacelinerRepo;
        this.shuttleRepo = shuttleRepo;
        this.regionRepo = regionRepo;
        this.portRepo = portRepo;
        this.padRepo = padRepo;
    }

    @BeforeAll
    public void beforeAll() {
        for (Spaceliner s : AstrodiaData.SPACELINERS) {
            spacelinerRepo.save(s);
        }
        for (Shuttle s : AstrodiaData.SHUTTLES) {
            shuttleRepo.save(s);
        }
        for (Region r : AstrodiaData.REGIONS) {
            regionRepo.save(r);
        }
        for (Port p : AstrodiaData.PORTS) {
            portRepo.save(p);
        }
        for (Pad p : AstrodiaData.PADS) {
            padRepo.save(p);
        }
        Flight f1 = Flight.builder().id(0).flightCode("ABC123").departing(TODAY).launchPad(DEP_PAD).arriving(TOMORROW).arrivalPad(ARR_PAD).shuttle(SHUTTLE).build();
        Flight f2 = Flight.builder().id(1).flightCode("ABC123").departing(TODAY).launchPad(DEP_PAD).arriving(TOMORROW).arrivalPad(ARR_PAD).shuttle(SHUTTLE).build();
        flightRepo.save(f1);
        flightRepo.save(f2);
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void afterAll() {

    }

    @Test
    @Order(1)
    void saveFlightTest() {
        Flight flight = Flight.builder()
                .flightCode(FLIGHT_CODE)
                .departing(TODAY)
                .launchPad(DEP_PAD)
                .arriving(TOMORROW)
                .arrivalPad(ARR_PAD)
                .shuttle(SHUTTLE).build();
        flightRepo.save(flight);

        assertThat(flight.getId()).isNotNull();
    }

    @Test
    @Order(2)
    void getFlightTest() {
        Flight f1 = Flight.builder().id(0).flightCode("ABC123").departing(TODAY).launchPad(DEP_PAD).arriving(TOMORROW).arrivalPad(ARR_PAD).shuttle(SHUTTLE).build();
        Flight f2 = Flight.builder().id(1).flightCode("XYZ123").departing(TODAY).launchPad(DEP_PAD).arriving(TOMORROW).arrivalPad(ARR_PAD).shuttle(SHUTTLE).build();
        flightRepo.save(f1);
        flightRepo.save(f2);
        Flight f = flightRepo.findById(1);
        assertThat(f).isNotNull();
    }

    @Test
    @Order(3)
    void getListOfFlights() {
        List<Flight> flights = flightRepo.findAll();
        log.warn(flights.toString());
        assertThat(flights).isNotEmpty();
    }


}
