package org.nicholasshore.astrodia.repositories;

import org.nicholasshore.astrodia.models.Port;
import org.nicholasshore.astrodia.models.Region;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
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
class PortRepositoryTests {
    final String ID = "TPT";
    final String NAME = "TestPort";
    final String LOCATION = "PortLocation, NL";

    @Autowired
    PortRepository portRepository;
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
    void savePortTest() {
        Region region = new Region("XX", "TestPortRegion");
        regionRepository.save(region);

        Port port = Port.builder()
                .id(ID)
                .name(NAME)
                .location(LOCATION)
                .region(region).build();
        portRepository.save(port);
        assertThat(portRepository.findAll()).contains(port);
    }
    @Test
    @Order(2)
    void getPortTest() {
        Port port = portRepository.findById(ID).get();
        assertThat(port).isNotNull();
    }

    @Test
    @Order(3)
    void getListOfPortsTest() {
        List<Port> ports = portRepository.findAll();
        assertThat(ports.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    void updatePortTest() {
        String updatedPortName = "UpdatePortX";
        Port port = portRepository.findById(ID).get();
        port.setName(updatedPortName);
        Port portUpdated = portRepository.save(port);
        assertThat(portUpdated.getName()).isEqualTo(updatedPortName);
    }

    @Test
    @Order(5)
    void deletePortTest() {
        Port port = portRepository.findById(ID).get();
        portRepository.delete(port);
        Port port1 = null;
        Optional<Port> optionalPort = portRepository.findById(ID);
        if(optionalPort.isPresent()){
            port1 = optionalPort.get();
        }
        assertThat(port1).isNull();
    }
}
