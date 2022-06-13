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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String code;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shuttle_port_id", nullable = false)
    private Port portId;
    @JsonBackReference
    @OneToMany(mappedBy = "launchPad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> departingFlights;
    @JsonBackReference
    @OneToMany(mappedBy = "arrivalPad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> arrivingFlights;

}
