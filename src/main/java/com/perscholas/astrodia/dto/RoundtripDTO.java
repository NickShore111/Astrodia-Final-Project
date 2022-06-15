package com.perscholas.astrodia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoundtripDTO {
    String departurePort;
    String arrivalPort;
    @DateTimeFormat(pattern="mm/dd/yyyy")
    Date departureDate;
    @DateTimeFormat(pattern="mm/dd/yyyy")
    Date arrivalDate;
}
