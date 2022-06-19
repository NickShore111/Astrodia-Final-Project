package com.perscholas.astrodia.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoundtripDTO {
    @NotEmpty(message = "{NotEmpty.Flight.DeparturePort}")
    String departurePort;
    @NotEmpty(message = "{NotEmpty.Flight.ArrivalPort}")
    String arrivalPort;
    @DateTimeFormat(pattern = "mm/dd/yyyy")
    Date departureDate;
    @DateTimeFormat(pattern = "mm/dd/yyyy")
    Date arrivalDate;
}
