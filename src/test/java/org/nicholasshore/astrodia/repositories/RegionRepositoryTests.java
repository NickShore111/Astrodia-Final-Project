package org.nicholasshore.astrodia.repositories;

import org.nicholasshore.astrodia.models.Region;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.Standard.class)
@Rollback(value = false)
class RegionRepositoryTests {
    final String ID = "TR";
    final String NAME = "Test Region";

    @Autowired
    RegionRepository regionRepository;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @BeforeAll
    static void beforeAll() {

    }

    @AfterAll
    static void afterAll() {}

    @Test
    @Order(1)
    void saveRegionTest() {
        Region region = Region.builder()
                .id(ID)
                .name(NAME).build();
        regionRepository.save(region);
        assertThat(regionRepository.findAll()).contains(region);
    }
    @Test
    @Order(2)
    void getRegionTest() {
        Region region = regionRepository.findById(ID).get();
        assertThat(region).isNotNull();
    }

    @Test
    @Order(3)
    void getListOfRegionsTest() {
        List<Region> regions = regionRepository.findAll();
        assertThat(regions.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    void updateRegionTest() {
        String updatedRegionName = "UpdateRegionX";
        Region region = regionRepository.findById(ID).get();
        region.setName(updatedRegionName);
        Region regionUpdated = regionRepository.save(region);
        assertThat(regionUpdated.getName()).isEqualTo(updatedRegionName);

    }

    @Test
    @Order(5)
    void deleteRegionTest() {
        Region region = regionRepository.findById(ID).get();
        regionRepository.delete(region);
        Region region1 = null;
        Optional<Region> optionalRegion = regionRepository.findById(ID);
        if(optionalRegion.isPresent()){
            region1 = optionalRegion.get();
        }
        assertThat(region1).isNull();
    }

    @ParameterizedTest
    @Order(6)
    @CsvSource({ "MO, Moon", "MA, Mars", "EO, Earth Orbit", "ES, Earth Surface"})
    void parameterizedTest(String id, String name) {

        Region r = regionRepository.save(new Region(id, name));

        assertThat(regionRepository.findById(id)).isNotNull();
    }
}
