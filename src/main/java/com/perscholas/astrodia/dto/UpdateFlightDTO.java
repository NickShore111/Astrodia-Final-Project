package com.perscholas.astrodia.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFlightDTO {
    Integer id;
    String flightCode;
    String departingDate;
    String departingTime;
    String arrivalDate;
    String arrivalTime;
    Integer seatsAvailable;
    Integer pricePerSeat;
    String departurePad;
    String arrivalPad;
    String shuttleID;
}
