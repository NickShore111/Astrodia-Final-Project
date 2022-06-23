package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Region;
import com.perscholas.astrodia.models.Spaceliner;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.junit.platform.suite.api.Suite;
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
class RegionRepositoryTests {
    final String ID = "TT";
    final String NAME = "Test Region";

    @Autowired
    RegionRepository regionRepository;

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
}
