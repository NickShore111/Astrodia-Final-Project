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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String code;
    @NonNull
    private String name;
    @NonNull
    private Integer passenger_capacity;
    @JsonBackReference
    @OneToMany(mappedBy = "shuttleId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flights;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "spaceLinerId", nullable = false)
    private Spaceliner spacelinerId;
}
