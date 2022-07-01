package org.nicholasshore.astrodia.repositories;

import org.nicholasshore.astrodia.models.Spaceliner;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Suite
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.Standard.class)
@Rollback(value = false)
class SpacelinerRepositoryTests {
    final String ID = "NTS";
    final String NAME = "NewTestLiner";
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
    void saveSpacelinerTest() {
        Spaceliner spaceliner = Spaceliner.builder()
                .id(ID)
                .name(NAME).build();
        spacelinerRepository.save(spaceliner);
        assertThat(spacelinerRepository.findAll()).contains(spaceliner);
    }
    @Test
    @Order(2)
    void getSpacelinerTest() {
        Spaceliner spaceliner = spacelinerRepository.findById(ID).get();
        assertThat(spaceliner).isNotNull();
    }

    @Test
    @Order(3)
    void getListOfSpacelinersTest() {
        List<Spaceliner> spaceliners = spacelinerRepository.findAll();
        assertThat(spaceliners.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    void updateSpacelinerTest() {
        String updatedSpacelinerName = "UpdateTestX";
        Spaceliner spaceliner = spacelinerRepository.findById(ID).get();
        spaceliner.setName(updatedSpacelinerName);
        Spaceliner spacelinerUpdated = spacelinerRepository.save(spaceliner);
        assertThat(spacelinerUpdated.getName()).isEqualTo(updatedSpacelinerName);
    }

    @Test
    @Order(5)
    void deleteSpacelinerTest() {
        Spaceliner spaceliner = spacelinerRepository.findById(ID).get();
        spacelinerRepository.delete(spaceliner);
        Spaceliner spaceliner1 = null;
        Optional<Spaceliner> optionalSpaceliner = spacelinerRepository.findById(ID);
        if(optionalSpaceliner.isPresent()){
            spaceliner1 = optionalSpaceliner.get();
        }
        assertThat(spaceliner1).isNull();
    }
}
