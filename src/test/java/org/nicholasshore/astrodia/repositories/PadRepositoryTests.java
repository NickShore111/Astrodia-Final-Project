package org.nicholasshore.astrodia.repositories;

import org.nicholasshore.astrodia.models.Pad;
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
class PadRepositoryTests {
    final String ID = "TP";
    Region region = new Region("XXX", "RegionX");
    Port port;

    @Autowired
    PadRepository padRepository;
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
    void savePadTest() {
        regionRepository.save(region);
        port = new Port("XX", "PortX", "PortLocation, XX", region);
        portRepository.save(port);
        Pad pad = Pad.builder()
                .id(ID)
                .port(port).build();
        padRepository.save(pad);
        assertThat(padRepository.findAll()).contains(pad);
    }
    @Test
    @Order(2)
    void getPadTest() {
        Pad pad = padRepository.findById(ID).get();
        assertThat(pad).isNotNull();
    }

    @Test
    @Order(3)
    void getListOfPadsTest() {
        List<Pad> pads = padRepository.findAll();
        assertThat(pads.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    void updatePadTest() {

        Port updatedPadPort = portRepository.save(new Port("X2", "NewPortPadTest","Location, XX", region));
        Pad pad = padRepository.findById(ID).get();
        pad.setPort(updatedPadPort);
        Pad padUpdated = padRepository.save(pad);
        assertThat(padUpdated.getPort()).isEqualTo(updatedPadPort);
    }

    @Test
    @Order(5)
    void deletePadTest() {
        Pad pad = padRepository.findById(ID).get();
        padRepository.delete(pad);
        Pad pad1 = null;
        Optional<Pad> optionalPad = padRepository.findById(ID);
        if(optionalPad.isPresent()){
            pad1 = optionalPad.get();
        }
        assertThat(pad1).isNull();
    }
}
