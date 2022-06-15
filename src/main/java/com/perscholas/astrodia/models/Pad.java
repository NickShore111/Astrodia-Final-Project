package com.perscholas.astrodia.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
@Table(name = "pads")
public class Pad {
    @Id @NonNull
    String id;
    @NonNull
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "portId", nullable = false)
    Port port;
    @JsonBackReference
    @OneToMany(mappedBy = "launchPad", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Flight> departingFlights;
    @JsonBackReference
    @OneToMany(mappedBy = "arrivalPad", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Flight> arrivingFlights;

    public void addDepartureFlight(Flight f) {
        this.departingFlights.add(f);
    }
    public void addArrivalFlight(Flight f) {
        this.arrivingFlights.add(f);
    }

}
