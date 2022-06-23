package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Shuttle;
import com.perscholas.astrodia.models.Spaceliner;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.Standard.class)
@Rollback(value = false)
class ShuttleRepositoryTests {
    final String ID = "TSH";
    final String NAME = "TestShuttle";
    @Autowired
    ShuttleRepository shuttleRepository;
    @Autowired
    SpacelinerRepository spacelinerRepository;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @BeforeAll
    static void beforeAll() {}

    @AfterAll
    static void afterAll() {}

    @Test
    @Order(1)
    void saveShuttleTest() {
        Spaceliner testLiner = new Spaceliner("XXX","SpacelinerShuttleTest");
        spacelinerRepository.save(testLiner);

        Shuttle shuttle = Shuttle.builder()
                .id(ID)
                .name(NAME)
                .passengerCapacity(10)
                .spaceliner(testLiner)
                .build();
        shuttleRepository.save(shuttle);
        assertThat(shuttleRepository.findAll()).contains(shuttle);
    }

    @Test
    @Order(2)
    void getShuttleTest() {
        Shuttle shuttle = shuttleRepository.findById(ID).get();
        assertThat(shuttle).isNotNull();
    }

    @Test
    @Order(3)
    void getListOfShuttlesTest() {

        List<Shuttle> shuttles = shuttleRepository.findAll();
        assertThat(shuttles.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    void updateShuttleTest() {
        String updatedShuttleName = "UpdateShuttleTestX";
        Shuttle shuttle = shuttleRepository.findById(ID).get();
        shuttle.setName(updatedShuttleName);
        Shuttle shuttleUpdated = shuttleRepository.save(shuttle);
        assertThat(shuttleUpdated.getName()).isEqualTo(updatedShuttleName);
    }

    @Test
    @Order(5)
    void deleteShuttleTest() {
        Shuttle shuttle = shuttleRepository.findById(ID).get();
        shuttleRepository.delete(shuttle);
        Shuttle shuttle1 = null;
        Optional<Shuttle> optionalShuttle = shuttleRepository.findById(ID);
        if(optionalShuttle.isPresent()){
            shuttle1 = optionalShuttle.get();
        }
        assertThat(shuttle1).isNull();
    }

}
