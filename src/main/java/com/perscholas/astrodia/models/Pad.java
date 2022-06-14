package com.perscholas.astrodia.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "pads")
public class Pad {
    @Id @NonNull
    private String id;
    @NonNull
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "portId", nullable = false)
    private Port port;
    @JsonBackReference
    @OneToMany(mappedBy = "launchPad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> departingFlights;
    @JsonBackReference
    @OneToMany(mappedBy = "arrivalPad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> arrivingFlights;

    public void addDepartureFlight(Flight f) {
        this.departingFlights.add(f);
    }
    public void addArrivalFlight(Flight f) {
        this.arrivingFlights.add(f);
    }

}
