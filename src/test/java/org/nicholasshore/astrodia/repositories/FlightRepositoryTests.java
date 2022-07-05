package org.nicholasshore.astrodia.repositories;

import org.nicholasshore.astrodia.models.*;
import org.nicholasshore.astrodia.util.AstrodiaData;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DataJpaTest
class FlightRepositoryTests {
    FlightRepository flightRepo;
    SpacelinerRepository spacelinerRepo;
    ShuttleRepository shuttleRepo;
    RegionRepository regionRepo;
    PortRepository portRepo;
    PadRepository padRepo;

    long NOW = System.currentTimeMillis();
    long ONE_DAY = 86400000L;

    Timestamp TODAY = new Timestamp(NOW);
    Timestamp TOMORROW = new Timestamp(NOW+ONE_DAY);
    Timestamp ONE_WEEK_FROM_TODAY = new Timestamp(NOW + (ONE_DAY * 7));
    final String FLIGHT_CODE1 = "ABC123 X1-V2";
    final Pad LAUNCH_PAD1 = AstrodiaData.PADS.get(0);
    final Pad ARRIVAL_PAD1 = AstrodiaData.PADS.get(AstrodiaData.PADS.size()-1);
    final Shuttle SHUTTLE1 = AstrodiaData.SHUTTLES.get(1);

    final String FLIGHT_CODE2 = "XYZ789 A1-B2";
    final Pad LAUNCH_PAD2 = AstrodiaData.PADS.get(2);
    final Pad ARRIVAL_PAD2 = AstrodiaData.PADS.get(AstrodiaData.PADS.size()-2);
    final Shuttle SHUTTLE2 = AstrodiaData.SHUTTLES.get(0);

    Flight testFlight1 = Flight.builder()
            .id(2)
            .flightCode(FLIGHT_CODE1)
            .departing(TODAY)
            .arriving(TOMORROW)
            .launchPad(LAUNCH_PAD1)
            .arrivalPad(ARRIVAL_PAD1)
            .pricePerSeat(1200)
            .seatsAvailable(2)
            .shuttle(SHUTTLE1).build();

    Flight testFlight2 = Flight.builder()
            .id(1)
            .flightCode(FLIGHT_CODE2)
            .departing(TODAY)
            .arriving(ONE_WEEK_FROM_TODAY)
            .launchPad(LAUNCH_PAD2)
            .arrivalPad(ARRIVAL_PAD2)
            .pricePerSeat(1400)
            .seatsAvailable(4)
            .shuttle(SHUTTLE2).build();

    @Autowired
    public FlightRepositoryTests(FlightRepository flightRepo,
                                 SpacelinerRepository spacelinerRepo,
                                 ShuttleRepository shuttleRepo,
                                 RegionRepository regionRepo,
                                 PortRepository portRepo,
                                 PadRepository padRepo) {
        this.flightRepo = flightRepo;
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
    }

    @BeforeEach
    void setUp() {
        flightRepo.save(testFlight1);
        flightRepo.save(testFlight2);

    }

    @AfterEach
    void tearDown() {
        flightRepo.deleteAll();
    }

    @AfterAll
    static void afterAll() {

    }

    @Test
    @Order(1)
    void saveFlightTest() {
        flightRepo.save(testFlight1);
        assertThat(testFlight1.getId()).isNotNull();
    }

    @Test
    @Order(2)
    void getFlightTest() {
        testFlight1.setId(3);
        flightRepo.save(testFlight1);
        Flight f = flightRepo.findById(3);
        assertThat(f).isEqualTo(testFlight1);
    }

    @Test
    @Order(3)
    void getListOfFlights() {
        List<Flight> flights = flightRepo.findAll();
        assertThat(flights).isNotEmpty();
    }

    @Test
    @Order(4)
    void updateFlightTest() {
        String updatedFlightCode = "XXX99 X1-X2";
        Flight flight = flightRepo.findAll().get(0);
        Integer id = flight.getId();
        flight.setFlightCode(updatedFlightCode);
        flightRepo.save(flight);
        Flight updateFlight = flightRepo.findById(id).get();
        assertThat(updateFlight.getFlightCode()).isEqualTo(updatedFlightCode);
    }

    @Test
    @Order(5)
    void deleteFlightTest() {
        Flight flight = flightRepo.findAll().get(0);
        Integer ID = flight.getId();
        flightRepo.delete(flight);
        Flight flight1 = null;
        Optional<Flight> optionalFlight = flightRepo.findById(ID);
        if (optionalFlight.isPresent()){
            flight1 = optionalFlight.get();
        }
        assertThat(flight1).isNull();
    }

}
