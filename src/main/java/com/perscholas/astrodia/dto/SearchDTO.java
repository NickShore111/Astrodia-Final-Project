package com.perscholas.astrodia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
    @NotEmpty(message = "{NotEmpty.Flight.Departure}")
    String leaving;
    @NotEmpty(message = "{NotEmpty.Flight.Arrival}")
    String destination;
    @NotEmpty(message = "{NotEmpty.Flight.Date}")
    String departureDate;
    @NotEmpty(message = "{NotEmpty.Flight.Date}")
    String arrivalDate;
}
