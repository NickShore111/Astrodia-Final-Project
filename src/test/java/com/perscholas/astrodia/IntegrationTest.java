package com.perscholas.astrodia;

import com.perscholas.astrodia.models.Flight;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getFlight_returnsFlightDetails() throws Exception {
        //arrange

        //act
        ResponseEntity<Flight> response = restTemplate.getForEntity("/astrodia/api/flights/1", Flight.class);

        //assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
