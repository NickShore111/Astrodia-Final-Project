package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Flight;
import com.perscholas.astrodia.models.Pad;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DataJpaTest
class FlightRepositoryTest {

    FlightRepository flightRepository;
    SpacelinerRepository spacelinerRepository;

    @BeforeAll
    static void beforeAll() {

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

    // JUnit test for saving Flight
//    @Test
//    void saveFlightTest() {
//        long now = System.currentTimeMillis();
//        long tomorrow = now + 86400000;
//        Flight flight = Flight.builder()
//                .flightCode("ABC123 X1-J5")
//                .departing(new Timestamp(now))
//                .arriving(new Timestamp(tomorrow))
//                .seatsAvailable(10)
//                .pricePerSeat(10000)
//                .launchPad(pad1)
//                .arrivalPad(pad2)
//                .shuttle(shuttle1);
//    }

}
