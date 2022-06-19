package com.perscholas.astrodia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    @NotEmpty(message = "{NotEmpty.Flight.Departure}")
    String leaving;
    @NotEmpty(message = "{NotEmpty.Flight.Arrival}")
    String destination;
    @NotEmpty(message = "{NotEmpty.Flight.Date}")
    String departureDate;
    @NotEmpty(message = "{NotEmpty.Flight.Date}")
    String arrivalDate;
}
