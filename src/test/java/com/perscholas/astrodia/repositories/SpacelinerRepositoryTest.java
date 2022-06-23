package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Spaceliner;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DataJpaTest
class SpacelinerRepositoryTest {

    final List<Spaceliner> allValidSpaceliners =
            new ArrayList<>(Arrays.asList(
                    new Spaceliner("SPX", "SpaceX"),
                    new Spaceliner("VGN", "Virgin Galactic"),
                    new Spaceliner("BLO", "Blue Origin")
            ));
    @Autowired
    SpacelinerRepository spacelinerRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @BeforeAll
    static void beforeAll() {

    }

    @AfterAll
    static void afterAll() {

    }

    @Test
    void saveSpaceliner() {

        Spaceliner spaceliner = Spaceliner.builder()
                .id("TST")
                .name("TestLinerX").build();

        spacelinerRepository.save(spaceliner);

        Assertions.assertThat(spacelinerRepository.findAll()).contains(spaceliner);
    }
}
