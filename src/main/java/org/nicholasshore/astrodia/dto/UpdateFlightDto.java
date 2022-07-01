package org.nicholasshore.astrodia.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFlightDto {
    Integer id;
    String flightCode;
    String departureDate;
    String departureTime;
    String arrivalDate;
    String arrivalTime;
    Integer seatsAvailable;
    Integer pricePerSeat;
    String departurePadID;
    String arrivalPadID;
    String shuttleID;
}
//        th:field="*{id}"
//        th:field="*{flightCode}"
//        th:field="*{seatsAvailable}"
//        th:field="*{pricePerSeat}"
//        th:field="*{shuttleID}"
//        th:field="*{departurePadID}"
//        th:field="*{departureDate}"
//        th:field="*{departureTime}"
//        th:field="*{arrivalPadID}"
//        th:field="*{arrivalDate}"
//        th:field="*{arrivalTime}"
