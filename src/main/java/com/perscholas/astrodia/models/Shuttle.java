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
@Table(name = "shuttles")
public class Shuttle {
    @Id @NonNull
    private String id;
    @NonNull
    private String name;
    @NonNull
    private Integer passengerCapacity;
    @JsonBackReference
    @OneToMany(mappedBy = "shuttle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flights;
    @NonNull
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "spaceLinerId", nullable = false)
    private Spaceliner spacelinerId;

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }
}
