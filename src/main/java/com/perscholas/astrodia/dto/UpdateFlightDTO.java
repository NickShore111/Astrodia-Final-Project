package com.perscholas.astrodia.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.perscholas.astrodia.models.Pad;
import com.perscholas.astrodia.models.Shuttle;
import lombok.NonNull;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.sql.Timestamp;

public class UpdateFlightDTO {
    Integer id;
    String flightCode;
    String departingDate;
    String departingTime;
    String arrivingDate;
    String arrivingTime;
    Integer seatsAvailable;
    int pricePerSeat;
    String launchPad;
    String arrivalPad;
    String shuttle;
}
